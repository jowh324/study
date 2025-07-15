package com.example.studygroup.Service;

import com.example.studygroup.DTO.CategoryDto;
import com.example.studygroup.DTO.CategoryRequestDto;
import com.example.studygroup.Entitiy.Category;
import com.example.studygroup.Repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryDto create(CategoryRequestDto req) {
        Category category = Category.builder()
                .name(req.getName())
                .build();
        categoryRepository.save(category);
        return toDto(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> listAll() {
        return categoryRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDto toDto(Category c) {
        return new CategoryDto(c.getId(), c.getName());
    }
}