package com.allegro.Entity;

import lombok.Data;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "postgresproduct")
public class PostgresProduct {
    @Id
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
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    public PostgresProduct() {
    }

    public PostgresProduct(String name, String description) {
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
