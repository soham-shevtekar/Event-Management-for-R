package com.example.demo.service;

import com.example.demo.Attendee.CategoryEntity;
import com.example.demo.repo.CategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepository;

    // Method to check if a category exists by name
    public boolean categoryExists(String categoryName) {
        Optional<CategoryEntity> category = categoryRepository.findByName(categoryName);
        return category.isPresent();
    }

    // Add a new category
    public void addCategory(String categoryName) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryName);
        categoryRepository.save(category);
    }

    // Get all categories
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Additional method if needed to get a category by name
    public Optional<CategoryEntity> getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }
}
