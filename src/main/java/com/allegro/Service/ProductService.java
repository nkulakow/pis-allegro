package com.allegro.Service;

import com.allegro.Document.MongoProduct;
import com.allegro.Entity.Category;
import com.allegro.Entity.PostgresProduct;
import com.allegro.DTO.ProductDTO;
import com.allegro.Repository.PostgresProductRepository;
import com.allegro.Repository.MongoProductRepository;
import com.allegro.utilis.IdGenerator;
import jakarta.transaction.Transactional;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final MongoProductRepository mongoProductRepository;

    private final PostgresProductRepository postgresProductRepository;

    @Autowired
    ProductService(MongoProductRepository mongoProductRepository, PostgresProductRepository repository){
        this.mongoProductRepository = mongoProductRepository;
        this.postgresProductRepository  = repository;
    }

    @Transactional
    public void addProduct(String name, List<Category> categories, float price, String description, MultipartFile photo) throws IOException {
        String id = IdGenerator.generateId();
        List<Binary> photos = null;
        if (photo != null) {
            byte[] photoData = photo.getBytes();
            photos = new ArrayList<>();
            photos.add(new Binary(photoData));
        }
        ProductDTO productDTO = new ProductDTO(id, name, categories, price, description, photos);
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

    public List<ProductDTO> findByText(String text) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(text);
        var postgresList = postgresProductRepository.searchProducts(text);
        var mongoList = mongoProductRepository.findAllBy(criteria, Sort.by("description"));
        var productList = new ArrayList<ProductDTO>();
        var seenProductIds = new HashSet<String>();

        for (var postProd : postgresList) {
            var targetId = postProd.getId();
            if (!seenProductIds.contains(targetId)) {
                Optional<MongoProduct> mProdFound = mongoProductRepository.findById(targetId);
                mProdFound.ifPresent(mongoProduct -> {
                    productList.add(new ProductDTO(postProd, mongoProduct));
                    seenProductIds.add(targetId);
                });
            }
        }

        for (var mongoProd : mongoList) {
            var targetId = mongoProd.getId();
            if (!seenProductIds.contains(targetId)) {
                Optional<PostgresProduct> pProdFound = postgresProductRepository.findById(targetId);
                pProdFound.ifPresent(postgresProd -> {
                    productList.add(new ProductDTO(postgresProd, mongoProd));
                    seenProductIds.add(targetId);
                });
            }
        }

        return productList;
    }
}
