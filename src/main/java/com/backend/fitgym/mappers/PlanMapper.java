package com.backend.fitgym.mappers;

import org.springframework.stereotype.Component;
import com.backend.fitgym.entities.Plan;
import com.backend.fitgym.dto.PlanDTO;

@Component
public class PlanMapper {

    public PlanDTO toDTO(Plan plan, Long memberCount) {
        PlanDTO dto = new PlanDTO();
        dto.setId(plan.getId());
        dto.setName(plan.getName());
        dto.setPrice(plan.getPrice());
        dto.setMemberCount(memberCount);
        return dto;
    }

    public Plan toEntity(PlanDTO dto) {
        Plan plan = new Plan();
        plan.setId(dto.getId());
        plan.setName(dto.getName());
        plan.setPrice(dto.getPrice());
        return plan;
    }
}
