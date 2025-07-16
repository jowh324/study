package com.example.studygroup.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class StudyPlanDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isComplete;
    private LocalDateTime createdAt;

    public StudyPlanDto() {}

    public StudyPlanDto(Long id, String title, String description,
                        LocalDate startDate, LocalDate endDate,
                        Boolean isComplete, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isComplete = isComplete;
        this.createdAt = createdAt;
    }

}
