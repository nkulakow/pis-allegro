package com.allegro.Entity;

import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Getter
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private String id;

    @Column
    @Getter
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private List<PostgresProduct> products;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
