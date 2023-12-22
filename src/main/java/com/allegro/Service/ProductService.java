package com.allegro.Service;

import com.allegro.Document.MongoProduct;
import com.allegro.Entity.PostgresProduct;
import com.allegro.DTO.ProductDTO;
import com.allegro.Repository.PostgresProductRepository;
import com.allegro.Repository.MongoProductRepository;
import com.allegro.utilis.IdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void addProduct(String name, String category, float price, String description){
        String id = IdGenerator.generateId();
        ProductDTO productDTO = new ProductDTO(id, name, category, price, description);
        postgresProductRepository.save(productDTO.getPostgres());
        mongoProductRepository.insert(productDTO.getMongo());
    }

    public List<MongoProduct> getMongoProducts(){
        return mongoProductRepository.findAll();
    }

    public List<PostgresProduct> getPostgresProducts (){
        return this.postgresProductRepository.findAll();
    }

    public ArrayList<ProductDTO> getProducts () {
        var mongoList = this.mongoProductRepository.findAll();
        var postgresList = this.postgresProductRepository.findAll();
        var productList = new ArrayList<ProductDTO>();
        for (var postProd: postgresList) {
            var targetId = postProd.getId();
            Optional<MongoProduct> mProdFound = mongoList.stream().filter(mp -> targetId.equals(mp.getId())).findFirst();
            if (mProdFound.isPresent()){
                MongoProduct mongoProd = mProdFound.get();
                productList.add(new ProductDTO(postProd, mongoProd));
            }
            else {
//                @TODO throw or sth else
            }
        }
        return productList;
    }
}
