package com.allegro.Document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("product")
public class MongoProduct {
    @Id
    private String id;

    @TextIndexed(weight = 2)
    private String name;

    @TextIndexed(weight = 1)
    private String description;

    public MongoProduct(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
