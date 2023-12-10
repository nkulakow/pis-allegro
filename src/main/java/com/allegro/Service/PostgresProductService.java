package com.allegro.Service;

import com.allegro.Entity.PostgresProduct;
import com.allegro.Repository.PostgresProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostgresProductService {


    @Autowired
    private final PostgresProductRepository repository;

    @Autowired
    PostgresProductService(PostgresProductRepository repository){
        this.repository  = repository;
    }

    public void addProduct(String name, String description){
        PostgresProduct product = new PostgresProduct(name, description);
        repository.save(product);
    }

    public List<PostgresProduct> getProducts (){
        return this.repository.findAll();
    }


}
