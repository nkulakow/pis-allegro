package com.allegro.Repository;

import com.allegro.Document.MongoProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoProductRepository extends MongoRepository<MongoProduct, String> {
}
