package com.allegro.Service;

import com.allegro.Entity.MongoProduct;
import com.allegro.Entity.PostgresProduct;
import com.allegro.Repository.PostgresProductRepository;
import com.allegro.Repository.MongoProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    MongoProductRepository mongoProductRepository;

    @Autowired
    private final PostgresProductRepository postgresProductRepository;

    @Autowired
    ProductService(PostgresProductRepository repository){
        this.postgresProductRepository  = repository;
    }

    @Transactional
    public void addProduct(String name, String category){
        PostgresProduct postgresProduct = new PostgresProduct(name, category);
        postgresProductRepository.save(postgresProduct);
        MongoProduct mongoProduct = new MongoProduct(name, category);
        mongoProductRepository.insert(mongoProduct);
    }

    public List<MongoProduct> getMongoProducts(){
        return mongoProductRepository.findAll();
    }

    public List<PostgresProduct> getPostgresProducts (){
        return this.postgresProductRepository.findAll();
    }
}
