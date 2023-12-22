package com.allegro.DTO;

import com.allegro.Document.MongoProduct;
import com.allegro.Entity.PostgresProduct;
import lombok.Getter;
import lombok.Setter;

public class ProductDTO {

    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String category;
    @Getter
    @Setter
    private float price;
    @Getter
    @Setter
    private String description;

    public ProductDTO() {
    }


    public ProductDTO(String id, String name, String category, float price, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public ProductDTO(PostgresProduct postgresProduct, MongoProduct mongoProduct){
        this.id = postgresProduct.getId();
        this.name = postgresProduct.getName();
        this.category = postgresProduct.getCategory();
        this.price = postgresProduct.getPrice();
        this.description = mongoProduct.getDescription();
    }

    public MongoProduct getMongo(){
        return new MongoProduct(this.id, this.name, this.description);
    }

    public PostgresProduct getPostgres(){
        return new PostgresProduct(this.id, this.name, this.category, this.price);
    }
}
