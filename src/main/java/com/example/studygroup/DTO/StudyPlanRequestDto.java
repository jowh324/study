package com.example.studygroup.DTO;

import java.time.LocalDate;

public class StudyPlanRequestDto {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isComplete;

    public StudyPlanRequestDto() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Boolean getIsComplete() { return isComplete; }
    public void setIsComplete(Boolean isComplete) { this.isComplete = isComplete; }
}
