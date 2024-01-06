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

    @Column
    @Getter
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PostgresProduct() {
    }

    public PostgresProduct(String id, String name, List<Category> categories, float price, int quantity, User user) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
    }

    @Override
    public String toString(){
        StringBuilder categoryNames = new StringBuilder();
        for (Category category : categories) {
            categoryNames.append(category.getCategoryName()).append(" ");
        }
        return this.name + " " + categoryNames + " " + this.price + " " + this.quantity;
    }
}
