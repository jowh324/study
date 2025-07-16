package com.example.studygroup.Service;

import com.example.studygroup.DTO.StudyPlanDto;
import com.example.studygroup.DTO.StudyPlanRequestDto;
import com.example.studygroup.Entitiy.StudyPlan;
import com.example.studygroup.Entitiy.Users;
import com.example.studygroup.Repository.StudyPlanRepository;
import com.example.studygroup.Repository.UserRepository;
import com.example.studygroup.exception.UnauthorizedException;
import com.example.studygroup.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyPlanService {
    private final StudyPlanRepository planRepository;
    private final UserRepository userRepository;

    public StudyPlanService(StudyPlanRepository planRepository, UserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public StudyPlanDto create(Long userId, StudyPlanRequestDto req) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        StudyPlan sp = StudyPlan.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .isComplete(req.getIsComplete())
                .user(user)
                .build();
        planRepository.save(sp);
        return toDto(sp);
    }

    @Transactional(readOnly = true)
    public List<StudyPlanDto> listByUser(Long userId) {
        return planRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudyPlanDto update(Long planId, Long currentUserId, StudyPlanRequestDto req) {
        StudyPlan sp = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        // 계획의 소유자 ID와 현재 로그인한 사용자 ID가 같은지 확인
        if (!sp.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You can only update your own plans.");
        }

        sp.setTitle(req.getTitle());
        sp.setDescription(req.getDescription());
        sp.setStartDate(req.getStartDate());
        sp.setEndDate(req.getEndDate());
        sp.setIsComplete(req.getIsComplete());
        StudyPlan updatedPlan = planRepository.save(sp);

        return toDto(updatedPlan);
    }

    @Transactional
    public void delete(Long planId, Long currentUserId) {
        StudyPlan sp = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        // 삭제 권한 확인
        if (!sp.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You can only delete your own plans.");
        }

        planRepository.deleteById(planId);
    }

    private StudyPlanDto toDto(StudyPlan sp) {
        return new StudyPlanDto(
                sp.getId(), sp.getTitle(), sp.getDescription(),
                sp.getStartDate(), sp.getEndDate(), sp.getIsComplete(),
                sp.getCreatedAt()
        );
    }
}
