package com.allegro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.allegro.Entity.PostgresProduct;


public interface PostgresProductRepository extends JpaRepository<PostgresProduct, String> {
}
