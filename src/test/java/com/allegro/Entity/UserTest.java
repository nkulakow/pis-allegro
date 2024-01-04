package com.allegro.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTest {

    @Test
    void testGetters() {
        String email = "abc@gmail;.com";
        String password = "abc";
        String name = "abc";
        String surname = "abc";
        User user = new User(email, password, name, surname, null, null);

        assertEquals(email, user.getEmail());
        assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", user.getPasswordHash());
        assertEquals(name, user.getName());
        assertEquals(surname, user.getSurname());
        assertNull(user.getSoldProducts());
        assertNull(user.getCartItems());
    }

    @Test
    void testAddSoldProduct(){
        User user = new User("abc@gmail.com", "abc", "abc", "abc", null, null);
        PostgresProduct product1 = new PostgresProduct("abc", "abc", null, 2, 2, user);
        PostgresProduct product2 = new PostgresProduct("def", "def", null, 2, 2, user);
        user.addSoldProduct(product1);
        user.addSoldProduct(product2);
        assertEquals(2, user.getSoldProducts().size());
    }

    @Test
    void testAddCartItem() {
        User user = new User("abc@gmail.com", "abc", "abc", "abc", null, null);
        PostgresProduct product1 = new PostgresProduct("abc", "abc", null, 2, 2, user);
        PostgresProduct product2 = new PostgresProduct("def", "def", null, 2, 2, user);
        CartItem cartItem1 = new CartItem(user, product1, 2);
        CartItem cartItem2 = new CartItem(user, product2, 2);
        user.addCartItem(cartItem1);
        user.addCartItem(cartItem2);
        assertEquals(2, user.getCartItems().size());
    }

}
