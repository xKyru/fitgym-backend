package com.backend.fitgym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.fitgym.dto.PlanDTO;
import com.backend.fitgym.services.PlanService;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = "*")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping
    public List<PlanDTO> getAllPlans() {
        return planService.getAllPlans();
    }

    @PostMapping
    public ResponseEntity<?> createPlan(@RequestBody PlanDTO planDTO) {
        try {
            PlanDTO createdPlan = planService.createPlan(planDTO);
            return ResponseEntity.ok(createdPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable Long id, @RequestBody PlanDTO planDTO) {
        try {
            PlanDTO updatedPlan = planService.updatePlan(id, planDTO);
            if (updatedPlan != null) {
                return ResponseEntity.ok(updatedPlan);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
        try {
            if (planService.deletePlan(id)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/member-count")
    public ResponseEntity<Long> getMemberCountByPlan(@PathVariable Long id) {
        Long count = planService.getMemberCountByPlan(id);
        return ResponseEntity.ok(count);
    }
}
