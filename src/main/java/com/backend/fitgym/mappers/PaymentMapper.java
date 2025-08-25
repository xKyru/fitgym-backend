package com.backend.fitgym.mappers;

import org.springframework.stereotype.Component;
import com.backend.fitgym.entities.*;
import com.backend.fitgym.dto.PaymentDTO;

@Component
public class PaymentMapper {

    public PaymentDTO toDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setDate(payment.getDate());
        dto.setMethod(payment.getMethod());
        dto.setStatus(payment.getStatus());
        dto.setInvoiceNumber(payment.getInvoiceNumber());

        if (payment.getMember() != null) {
            dto.setMemberId(payment.getMember().getId());
        }
        if (payment.getPlan() != null) {
            dto.setPlanId(payment.getPlan().getId());
        }

        return dto;
    }

    public Payment toEntity(PaymentDTO dto, Member member, Plan plan) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setAmount(dto.getAmount());
        payment.setDate(dto.getDate());
        payment.setMethod(dto.getMethod());
        payment.setStatus(dto.getStatus());
        payment.setInvoiceNumber(dto.getInvoiceNumber());
        payment.setMember(member);
        payment.setPlan(plan);
        return payment;
    }
}
