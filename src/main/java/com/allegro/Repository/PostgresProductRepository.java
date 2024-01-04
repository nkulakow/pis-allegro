package com.allegro.Repository;

import com.allegro.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.allegro.Entity.PostgresProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostgresProductRepository extends JpaRepository<PostgresProduct, String> {

    @Query(value = "SELECT p.*, c.id as category_id, c.category_name FROM product p " +
            "JOIN product_category pc ON p.id = pc.product_id " +
            "JOIN category c ON pc.category_id = c.id " +
            "WHERE to_tsvector('english', p.name) @@ to_tsquery('english', :query) " +
            "OR to_tsvector('english', c.category_name) @@ to_tsquery('english', :query)",
            nativeQuery = true)

    List<PostgresProduct> searchProducts(@Param("query") String query);

    List<PostgresProduct> findByUser_Id(String userId);

    List<PostgresProduct> findByCategories_CategoryNameIn(List<String> categories);
}
