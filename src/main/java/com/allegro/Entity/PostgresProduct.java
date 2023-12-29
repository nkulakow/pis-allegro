package com.allegro.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Entity
@Table(name = "product")
public class PostgresProduct {
    @Id
    @Column
    @Getter
    private String id;

    @Column
    @Getter
    private String name;

    @ManyToMany
    @Getter
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @Column
    @Getter
    private float price;

    public PostgresProduct() {
    }

    public PostgresProduct(String id, String name, List<Category> categories, float price) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.price = price;
    }

    @Override
    public String toString(){
        StringBuilder categoryNames = new StringBuilder();
        for (Category category : categories) {
            categoryNames.append(category.getCategoryName()).append(" ");
        }
        return this.name + " " + categoryNames + " " + this.price;
    }
}
