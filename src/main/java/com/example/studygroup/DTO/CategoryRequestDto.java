package com.example.studygroup.DTO;

import com.example.studygroup.Entitiy.CategoryType;

public class CategoryRequestDto {
    private String name;
    private CategoryType type;

    public CategoryRequestDto() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public CategoryType getType() { return type; }
    public void setType(CategoryType type) { this.type = type; }
}