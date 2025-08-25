package com.backend.fitgym.services;

import com.backend.fitgym.entities.Member;
import com.backend.fitgym.mappers.MemberMapper;
import com.backend.fitgym.repositories.MemberRepository;
import com.backend.fitgym.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.fitgym.dto.MemberDTO;
import com.backend.fitgym.entities.Plan;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberSearchService memberSearchService;

    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(memberMapper::toDTO)
                .toList();
    }

    public Optional<MemberDTO> getMemberById(Long id) {
        return memberRepository.findById(id).map(memberMapper::toDTO);
    }

    public MemberDTO createMember(MemberDTO memberDTO) {
        Plan plan = null;
        if (memberDTO.getPlanId() != null) {
            plan = planRepository.findById(memberDTO.getPlanId())
                    .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        }
        Member member = memberMapper.toEntity(memberDTO, plan);
        if (member.getFechaRegistro() == null) {
            member.setFechaRegistro(LocalDate.now());
        }

        Member savedMember = memberRepository.save(member);

        //Integrar a elasticsearch
        try{
            memberSearchService.indexMember(memberMapper.toDTO(savedMember));
        }catch (IOException e){
            e.printStackTrace();
        }

        return memberMapper.toDTO(savedMember);
    }

    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        return memberRepository.findById(id).map(member -> {
            Plan plan = null;
            if (memberDTO.getPlanId() != null) {
                plan = planRepository.findById(memberDTO.getPlanId())
                        .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
            }
            Member updated = memberMapper.toEntity(memberDTO, plan);
            updated.setId(member.getId());
            return memberMapper.toDTO(memberRepository.save(updated));
        }).orElse(null);
    }

    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<MemberDTO> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).map(memberMapper::toDTO);
    }

}
