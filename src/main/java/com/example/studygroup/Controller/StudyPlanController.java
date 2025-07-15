package com.example.studygroup.Controller;

import com.example.studygroup.DTO.StudyPlanDto;
import com.example.studygroup.DTO.StudyPlanRequestDto;
import com.example.studygroup.Service.StudyPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studyplans")
public class StudyPlanController {
    private final StudyPlanService planService;

    public StudyPlanController(StudyPlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<StudyPlanDto> createPlan(
            @RequestParam Long userId,
            @Valid @RequestBody StudyPlanRequestDto request
    ) {
        StudyPlanDto dto = planService.create(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<StudyPlanDto>> listPlans(@RequestParam Long userId) {
        return ResponseEntity.ok(planService.listByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyPlanDto> updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody StudyPlanRequestDto request
    ) {
        StudyPlanDto dto = planService.update(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.delete(id);
        return ResponseEntity.noContent().build();
    }
}