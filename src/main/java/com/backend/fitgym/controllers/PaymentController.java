package com.backend.fitgym.controllers;

import com.backend.fitgym.config.ElasticPaymentDataMigrator;
import com.backend.fitgym.services.PaymentSearchService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.fitgym.dto.PaymentDTO;
import com.backend.fitgym.services.PaymentService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentSearchService paymentSearchService;

    @GetMapping
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        Optional<PaymentDTO> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/member/{memberId}")
    public List<PaymentDTO> getPaymentsByMemberId(@PathVariable Long memberId) {
        return paymentService.getPaymentsByMemberId(memberId);
    }

    @PostMapping
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.createPayment(paymentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO updatedPayment = paymentService.updatePayment(id, paymentDTO);
        if (updatedPayment != null) {
            return ResponseEntity.ok(updatedPayment);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        if (paymentService.deletePayment(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/revenue/total")
    public BigDecimal getTotalRevenue() {
        return paymentService.getTotalRevenue();
    }

    @GetMapping("/member/{memberId}/latest")
    public List<PaymentDTO> getLatestMemberPayments(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "5") int limit) {
        return paymentService.getLatestMemberPayments(memberId, limit);
    }
}
