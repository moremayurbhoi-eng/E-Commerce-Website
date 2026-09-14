package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepositery;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepositery categoryRepository;

    // =====================================================
    // ADD CATEGORY
    // =====================================================

    @Override
    public Category saveCategory(Category category) {

        if (category.getName() == null ||
            category.getName().trim().isEmpty()) {

            throw new RuntimeException(
                "Category name is required"
            );
        }

        category.setName(
            category.getName().trim()
        );

        return categoryRepository.save(category);
    }

    // =====================================================
    // GET ALL CATEGORIES
    // =====================================================

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    // =====================================================
    // GET CATEGORY BY ID
    // =====================================================

    @Override
    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Category not found with id: " + id
                    )
                );
    }

    // =====================================================
    // UPDATE CATEGORY
    // =====================================================

    @Override
    public Category updateCategory(
            Long id,
            Category category) {

        Category existingCategory =
                categoryRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Category not found with id: " + id
                    )
                );

        if (category.getName() == null ||
            category.getName().trim().isEmpty()) {

            throw new RuntimeException(
                "Category name is required"
            );
        }

        existingCategory.setName(
            category.getName().trim()
        );

        return categoryRepository.save(
            existingCategory
        );
    }

    // =====================================================
    // DELETE CATEGORY
    // =====================================================

    @Override
    public void deleteCategory(Long id) {

        Category category =
                categoryRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Category not found with id: " + id
                    )
                );

        categoryRepository.delete(category);
    }
}