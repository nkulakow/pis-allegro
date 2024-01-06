package com.allegro.DTO;
import com.allegro.Entity.Category;
import com.allegro.Entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDTOTest {

    @Test
    void testGetters() {
        User user = new User("abc@gmail.com", "abc", "abc", "abc", null, null);
        String id = "abc";
        String name = "abc";
        String description = "abc";
        float price = 2;
        int quantity = 2;
        ProductDTO product = new ProductDTO(user, id, name, null, price, quantity, description, null);
        assertEquals(user, product.getUser());
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertNull(product.getPhotos());
    }

    @Test
    void testSetters() {
        User user = new User("abc@gmail.com", "abc", "abc", "abc", null, null);
        String id = "abc";
        String name = "abc";
        String description = "abc";
        int price = 2;
        int quantity = 2;
        ProductDTO product = new ProductDTO();
        product.setUser(user);
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPhotos(null);
        assertEquals(user, product.getUser());
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertNull(product.getPhotos());
    }

    @Test
    void testGetStringCategories() {
        User user = new User("abc@gmail.com", "abc", "abc", "abc", null, null);
        String id = "abc";
        String name = "abc";
        String description = "abc";
        int price = 2;
        int quantity = 2;
        ProductDTO product = new ProductDTO(user, id, name, null, price, quantity, description, null);
        Category category1 = new Category("abc");
        Category category2 = new Category("def");
        var categories = new ArrayList<Category>();
        categories.add(category1);
        categories.add(category2);
        product.setCategories(categories);
        assertTrue(product.getStringCategories().contains("abc"));
        assertTrue(product.getStringCategories().contains("def"));
    }


}
