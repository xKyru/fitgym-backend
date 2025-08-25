package com.backend.fitgym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.fitgym.entities.Plan;
import com.backend.fitgym.dto.PlanDTO;
import com.backend.fitgym.mappers.PlanMapper;
import com.backend.fitgym.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanMapper planMapper;

    public List<PlanDTO> getAllPlans() {
        return planRepository.findAll()
                .stream()
                .map(plan -> planMapper.toDTO(plan, planRepository.countMembersByPlanId(plan.getId())))
                .toList();
    }

    public PlanDTO createPlan(PlanDTO dto) {
        Optional<Plan> existingPlan = planRepository.findByName(dto.getName());
        if (existingPlan.isPresent()) {
            throw new RuntimeException("Ya existe un plan con el nombre: " + dto.getName());
        }

        Plan plan = planMapper.toEntity(dto);
        return planMapper.toDTO(planRepository.save(plan), 0L);
    }

    public PlanDTO updatePlan(Long id, PlanDTO dto) {
        return planRepository.findById(id).map(plan -> {
            if (!plan.getName().equals(dto.getName())) {
                Optional<Plan> planWithSameName = planRepository.findByName(dto.getName());
                if (planWithSameName.isPresent() && !planWithSameName.get().getId().equals(id)) {
                    throw new RuntimeException("Ya existe otro plan con el nombre: " + dto.getName());
                }
            }

            plan.setName(dto.getName());
            plan.setPrice(dto.getPrice());
            Plan updated = planRepository.save(plan);
            Long memberCount = planRepository.countMembersByPlanId(updated.getId());

            return planMapper.toDTO(updated, memberCount);
        }).orElse(null);
    }

    public boolean deletePlan(Long id) {
        Optional<Plan> optionalPlan = planRepository.findById(id);

        if (optionalPlan.isPresent()) {
            Long memberCount = planRepository.countMembersByPlanId(id);
            if (memberCount > 0) {
                throw new RuntimeException("No se puede eliminar el plan. Hay " + memberCount + " miembros usando este plan.");
            }

            planRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Long getMemberCountByPlan(Long planId) {
        return planRepository.countMembersByPlanId(planId);
    }
}
