package com.example.studygroup.DTO;

import com.example.studygroup.Entitiy.CategoryType;

public class CategoryDto {
    private Long id;
    private String name;
    private CategoryType type;

    public CategoryDto() {}
    public CategoryDto(Long id, String name, CategoryType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public CategoryType getType() { return type; }
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(CategoryType type) { this.type = type; }
}