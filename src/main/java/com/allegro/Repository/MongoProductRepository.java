package com.allegro.Repository;

import com.allegro.Document.MongoProduct;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoProductRepository extends MongoRepository<MongoProduct, String> {
    List<MongoProduct> findAllBy(TextCriteria criteria, Sort sort);
}
