package com.allegro.Document;

import org.jetbrains.annotations.Nullable;
import org.bson.types.Binary;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document("product")
public class MongoProduct {
    @Id
    private String id;
    @TextIndexed(weight = 1)
    private String description;

    @Nullable
    private List<Binary> photos;

    public MongoProduct(String id, String description, @Nullable List<Binary> photos) {
        this.id = id;
        this.description = description;
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public @Nullable List<Binary> getPhotos() {return this.photos;}

    @Override
    public String toString() {
        return this.description;
    }
}
