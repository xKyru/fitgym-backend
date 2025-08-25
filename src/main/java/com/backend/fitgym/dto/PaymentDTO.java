package com.backend.fitgym.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDTO {
    private Long id;
    private Long memberId;
    private Long planId;
    private BigDecimal amount;
    private LocalDate date;
    private String method;
    private String status;
    private String invoiceNumber;

    public PaymentDTO() {}

    public PaymentDTO(Long id, Long memberId, Long planId, BigDecimal amount, LocalDate date, String method, String status, String invoiceNumber) {
        this.id = id;
        this.memberId = memberId;
        this.planId = planId;
        this.amount = amount;
        this.date = date;
        this.method = method;
        this.status = status;
        this.invoiceNumber = invoiceNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}