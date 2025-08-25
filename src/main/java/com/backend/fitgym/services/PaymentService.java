package com.backend.fitgym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.fitgym.entities.*;
import com.backend.fitgym.dto.PaymentDTO;
import com.backend.fitgym.mappers.PaymentMapper;
import com.backend.fitgym.repositories.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentSearchService paymentSearchService;

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDTO)
                .toList();
    }

    public Optional<PaymentDTO> getPaymentById(Long id) {
        return paymentRepository.findById(id).map(paymentMapper::toDTO);
    }

    public List<PaymentDTO> getPaymentsByMemberId(Long memberId) {
        return paymentRepository.findByMemberId(memberId)
                .stream()
                .map(paymentMapper::toDTO)
                .toList();
    }

    public PaymentDTO createPayment(PaymentDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        Payment payment = paymentMapper.toEntity(dto, member, plan);

        Payment saverPayment = paymentRepository.save(payment);

        //Elasticseacrh
        try{
            paymentSearchService.indexPayment(paymentMapper.toDTO(saverPayment));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return paymentMapper.toDTO(payment);
    }

    public PaymentDTO createPaymentForMember(Long memberId, PaymentDTO dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        Payment payment = paymentMapper.toEntity(dto, member, plan);
        return paymentMapper.toDTO(paymentRepository.save(payment));
    }

    public PaymentDTO updatePayment(Long id, PaymentDTO dto) {
        return paymentRepository.findById(id).map(payment -> {
            Member member = memberRepository.findById(dto.getMemberId())
                    .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

            Plan plan = planRepository.findById(dto.getPlanId())
                    .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

            Payment updated = paymentMapper.toEntity(dto, member, plan);
            updated.setId(payment.getId());
            return paymentMapper.toDTO(paymentRepository.save(updated));
        }).orElse(null);
    }

    public boolean deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public BigDecimal getTotalRevenue() {
        List<Payment> completedPayments = paymentRepository.findByStatus("Completado");
        return completedPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<PaymentDTO> getLatestMemberPayments(Long memberId, int limit) {
        return paymentRepository.findLatestPaymentsByMemberId(memberId)
                .stream()
                .limit(limit)
                .map(paymentMapper::toDTO)
                .toList();
    }
}
