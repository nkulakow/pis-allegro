package com.allegro.DTO;

import com.allegro.Document.MongoProduct;
import com.allegro.Entity.Category;
import com.allegro.Entity.PostgresProduct;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.jetbrains.annotations.Nullable;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private int quantity;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @Nullable
    private List<Binary> photos;

    public ProductDTO() {
    }


    public ProductDTO(String id, String name, List<Category> category, float price, int quantity, String description, @Nullable List<Binary> photos) {
        this.id = id;
        this.name = name;
        this.categories = category;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.photos = photos;
    }

    public ProductDTO(PostgresProduct postgresProduct, MongoProduct mongoProduct){
        this.id = postgresProduct.getId();
        this.name = postgresProduct.getName();
        this.categories = postgresProduct.getCategories();
        this.price = postgresProduct.getPrice();
        this.quantity = postgresProduct.getQuantity();
        this.description = mongoProduct.getDescription();
        this.photos = mongoProduct.getPhotos();
    }

    public MongoProduct getMongo(){
        return new MongoProduct(this.id, this.description, this.photos);
    }

    public PostgresProduct getPostgres(){
        return new PostgresProduct(this.id, this.name, this.categories, this.price, this.quantity);
    }

    @Override
    public String toString() {
        StringBuilder categoryNames = new StringBuilder();
        for (Category category : categories) {
            categoryNames.append(category.getCategoryName()).append(" ");
        }
        return this.name + " " + categoryNames + " " + this.price + " " + this.quantity + " " + this.description;
    }

    public List<String> getBase64EncodedPhotos() {
        if (photos == null) {
            return Collections.emptyList();
        }

        return photos.stream()
                .map(this::convertBinaryToBase64)
                .collect(Collectors.toList());
    }

    private String convertBinaryToBase64(Binary binary) {
        byte[] data = binary.getData();
        return Base64.getEncoder().encodeToString(data);
    }

    public String getStringCategories(){
        StringBuilder text = new StringBuilder();
        for (var category: this.categories ) {
            text.append(" ").append(category.getCategoryName());
        }
        return text.toString();
    }
}
