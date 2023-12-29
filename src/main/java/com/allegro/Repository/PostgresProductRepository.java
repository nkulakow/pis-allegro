package com.allegro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.allegro.Entity.PostgresProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostgresProductRepository extends JpaRepository<PostgresProduct, String> {

    @Query(value = "SELECT * FROM product p " +
            "WHERE to_tsvector('english', p.name) @@ to_tsquery('english', :query) ",
            nativeQuery = true)
    List<PostgresProduct> searchProducts(@Param("query") String query);

}
