package com.allegro.Entity;

import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;

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

    @Column
    @Getter
    private String category;

    @Column
    @Getter
    private float price;

    public PostgresProduct() {
    }

    public PostgresProduct(String id, String name, String category, float price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString(){
        return this.name + " " + this.category + " " + this.price;
    }
}
