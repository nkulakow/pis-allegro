package com.allegro.Entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "product")
public class PostgresProduct {
    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    public PostgresProduct() {
    }

    public PostgresProduct(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return this.name + this.description;
    }
}
