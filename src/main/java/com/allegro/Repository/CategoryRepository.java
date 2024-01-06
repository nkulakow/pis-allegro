package com.allegro.Repository;

import com.allegro.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryName(String name);

    @Query(value = "SELECT category_name FROM category ", nativeQuery = true)
    List<String> getAllCategoryNames();
}
