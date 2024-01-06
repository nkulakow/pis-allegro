package com.allegro.DTO;
import com.allegro.Entity.Category;
import com.allegro.Entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ProductWithoutCategoryDTOTest {

    @Test
    void testGetters() {
        User user = new User("abc@gmail.com", "abc", "abc", "abc", null, null);
        String id = "abc";
        String name = "abc";
        String description = "abc";
        float price = 2;
        int quantity = 2;
        Category category1 = new Category("abc");
        Category category2 = new Category("def");
        var categories = new ArrayList<Category>();
        categories.add(category1);
        categories.add(category2);
        ProductDTO product1 = new ProductDTO(user, id, name, categories, price, quantity, description, null);
        ProductWithoutCategoryDTO product = new ProductWithoutCategoryDTO(product1);
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertNull(product.getPhotos());
        assertTrue(product.getCategories().contains("abc"));

    }

    @Test
    void testSetters() {
        String id = "abc";
        String name = "abc";
        String description = "abc";
        int price = 2;
        int quantity = 2;
        ProductWithoutCategoryDTO product = new ProductWithoutCategoryDTO();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPhotos(null);
        product.setCategories(new ArrayList<>(Collections.singleton("abc")));
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertNull(product.getPhotos());
        assertTrue(product.getCategories().contains("abc"));
    }


}
