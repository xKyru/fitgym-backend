package com.backend.fitgym.repositories;

import com.backend.fitgym.entities.Payment;
import com.backend.fitgym.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMember(Member member);

    List<Payment> findByMemberId(Long memberId);

    List<Payment> findByStatus(String status);

    @Query("SELECT p FROM Payment p WHERE p.member.id = :memberId ORDER BY p.date DESC")
    List<Payment> findLatestPaymentsByMemberId(@Param("memberId") Long memberId);


}
