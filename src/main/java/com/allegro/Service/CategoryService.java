package com.allegro.Service;

import com.allegro.Entity.Category;
import com.allegro.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(String name){
        categoryRepository.save(new Category(name));
    }

    public Category getCategoryByName(String name){
        return categoryRepository.findByCategoryName(name);
    }

    public List<Category> getCategoriesByIds(List<String> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
