package com.allegro.DTO;

import com.allegro.Document.MongoProduct;
import com.allegro.Entity.Category;
import com.allegro.Entity.PostgresProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProductDTO {

    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private List<Category> categories;
    @Getter
    @Setter
    private float price;
    @Getter
    @Setter
    private String description;

    public ProductDTO() {
    }


    public ProductDTO(String id, String name, List<Category> category, float price, String description) {
        this.id = id;
        this.name = name;
        this.categories = category;
        this.price = price;
        this.description = description;
    }

    public ProductDTO(PostgresProduct postgresProduct, MongoProduct mongoProduct){
        this.id = postgresProduct.getId();
        this.name = postgresProduct.getName();
        this.categories = postgresProduct.getCategories();
        this.price = postgresProduct.getPrice();
        this.description = mongoProduct.getDescription();
    }

    public MongoProduct getMongo(){
        return new MongoProduct(this.id, this.description);
    }

    public PostgresProduct getPostgres(){
        return new PostgresProduct(this.id, this.name, this.categories, this.price);
    }

    @Override
    public String toString() {
        StringBuilder categoryNames = new StringBuilder();
        for (Category category : categories) {
            categoryNames.append(category.getCategoryName()).append(" ");
        }
        return this.name + " " + categoryNames + " " + this.price + " " + this.description;
    }
}
