package com.backend.fitgym.mappers;

import org.springframework.stereotype.Component;
import com.backend.fitgym.dto.MemberDTO;
import com.backend.fitgym.entities.Member;
import com.backend.fitgym.entities.Plan;

@Component
public class MemberMapper {

    public MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setNombre(member.getNombre());
        dto.setEmail(member.getEmail());
        dto.setTelefono(member.getTelefono());
        dto.setDireccion(member.getDireccion());
        dto.setNacimiento(member.getNacimiento());
        dto.setEmergenciaNombre(member.getEmergenciaNombre());
        dto.setEmergenciaNumero(member.getEmergenciaNumero());
        dto.setImagenPerfil(member.getImagenPerfil());
        dto.setEstado(member.getEstado());
        dto.setFechaRegistro(member.getFechaRegistro());
        dto.setUltimoPago(member.getUltimoPago());
        dto.setSiguientePago(member.getSiguientePago());
        if (member.getPlan() != null) {
            dto.setPlanId(member.getPlan().getId());
        }
        return dto;
    }

    public Member toEntity(MemberDTO dto, Plan plan) {
        Member member = new Member();
        member.setId(dto.getId());
        member.setNombre(dto.getNombre());
        member.setEmail(dto.getEmail());
        member.setTelefono(dto.getTelefono());
        member.setDireccion(dto.getDireccion());
        member.setNacimiento(dto.getNacimiento());
        member.setEmergenciaNombre(dto.getEmergenciaNombre());
        member.setEmergenciaNumero(dto.getEmergenciaNumero());
        member.setImagenPerfil(dto.getImagenPerfil());
        member.setEstado(dto.getEstado());
        member.setFechaRegistro(dto.getFechaRegistro());
        member.setUltimoPago(dto.getUltimoPago());
        member.setSiguientePago(dto.getSiguientePago());
        member.setPlan(plan);
        return member;
    }
}
