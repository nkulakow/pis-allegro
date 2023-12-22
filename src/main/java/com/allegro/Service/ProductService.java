package com.allegro.Service;

import com.allegro.Entity.MongoProduct;
import com.allegro.Entity.PostgresProduct;
import com.allegro.Repository.PostgresProductRepository;
import com.allegro.Repository.MongoProductRepository;
import com.allegro.utilis.IdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final MongoProductRepository mongoProductRepository;

    @Autowired
    private final PostgresProductRepository postgresProductRepository;

    @Autowired
    ProductService(MongoProductRepository mongoProductRepository, PostgresProductRepository repository){
        this.mongoProductRepository = mongoProductRepository;
        this.postgresProductRepository  = repository;
    }

    @Transactional
    public void addProduct(String name, String category){
        String id = IdGenerator.generateId();
        PostgresProduct postgresProduct = new PostgresProduct(id, name, category);
        postgresProductRepository.save(postgresProduct);
        MongoProduct mongoProduct = new MongoProduct(id, name, category);
        mongoProductRepository.insert(mongoProduct);
    }

    public List<MongoProduct> getMongoProducts(){
        return mongoProductRepository.findAll();
    }

    public List<PostgresProduct> getPostgresProducts (){
        return this.postgresProductRepository.findAll();
    }
}
