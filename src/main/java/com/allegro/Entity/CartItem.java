package com.allegro.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private PostgresProduct product;

    @Column(nullable = false)
    private int quantity;

    public CartItem() {
    }

    public CartItem(User user, PostgresProduct product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

}
