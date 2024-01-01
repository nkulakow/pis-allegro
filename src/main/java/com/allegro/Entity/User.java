package com.allegro.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Data
@Entity
@Table(name = "users_table")
public class User {
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

    @Getter
    @Column(unique = true, nullable = false)
    private String email;

    @Getter
    @Column(nullable = false)
    private String passwordHash;

    @Getter
    @Column
    private String name;

    @Getter
    @Column
    private String surname;

    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostgresProduct> soldProducts;


    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;



    public User() {
    }

    public User(String email, String password, String name, String surname, List<PostgresProduct> soldProducts, List<CartItem> cartItems) {
        this.email = email;
        this.passwordHash = hashPassword(password);
        this.name = name;
        this.surname = surname;
        this.soldProducts = soldProducts;
        this.cartItems = cartItems;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexHash = new StringBuilder();

            for (byte hashByte : hashBytes) {
                hexHash.append(String.format("%02x", hashByte));
            }

            return hexHash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public void addSoldProduct(PostgresProduct product){
        if (this.soldProducts == null)
            this.soldProducts = List.of(product);
        else
            this.soldProducts.add(product);
    }
    public void addCartItem(CartItem cartItem){
        if (this.cartItems == null)
            this.cartItems = List.of(cartItem);
        else
            this.cartItems.add(cartItem);
    }

}
