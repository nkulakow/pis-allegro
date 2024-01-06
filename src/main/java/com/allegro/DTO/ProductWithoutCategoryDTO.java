package com.allegro.DTO;

import com.allegro.Entity.Category;
import com.allegro.Entity.User;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.jetbrains.annotations.Nullable;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductWithoutCategoryDTO {
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private List<String> categories;
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


    public ProductWithoutCategoryDTO() {
    }

    public ProductWithoutCategoryDTO(ProductDTO productDTO){
        this.id = productDTO.getId();
        this.name = productDTO.getName();
        this.price = productDTO.getPrice();
        this.description = productDTO.getDescription();
        this.photos = productDTO.getPhotos();
        this.quantity = productDTO.getQuantity();
        this.categories = productDTO.getCategories().stream().map(Category::getCategoryName).collect(Collectors.toList());
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


}
