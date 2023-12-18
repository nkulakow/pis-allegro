package com.allegro.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("product")
public class MongoProduct {
    @Id
    private String id;
    private String name;
    private String description;

    public MongoProduct(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name + " " + this.description;
    }
}
