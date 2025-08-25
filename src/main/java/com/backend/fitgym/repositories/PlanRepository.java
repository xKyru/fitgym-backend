package com.backend.fitgym.repositories;

import com.backend.fitgym.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByName(String name);



    @Query("SELECT p FROM Plan p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Plan> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.plan.id = :planId")
    Long countMembersByPlanId(@Param("planId") Long planId);

    @Query("SELECT p FROM Plan p ORDER BY p.price ASC")
    List<Plan> findAllOrderByPriceAsc();

    @Query("SELECT p FROM Plan p ORDER BY p.price DESC")
    List<Plan> findAllOrderByPriceDesc();

}
