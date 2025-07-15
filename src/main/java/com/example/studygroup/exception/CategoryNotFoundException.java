package com.example.studygroup.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String categoryName) {
        super("Category not found: " + categoryName);
    }
}
