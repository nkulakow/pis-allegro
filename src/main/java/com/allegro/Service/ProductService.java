package com.allegro.Service;

import com.allegro.Entity.Product;
import com.allegro.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public void addStudent(String name, String category){
        Product product = new Product(name, category);
        productRepository.insert(product);
    }

    public List<Product> getStudents(){
        return productRepository.findAll();
    }
}
