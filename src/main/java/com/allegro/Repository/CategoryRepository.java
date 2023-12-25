package com.allegro.Repository;

import com.allegro.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryName(String name);
}
