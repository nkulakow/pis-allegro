package com.allegro.Service;

import com.allegro.Entity.CartItem;
import com.allegro.Entity.PostgresProduct;
import com.allegro.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.allegro.Repository.CartItemRepository;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
    }

    public void addCartItem(User user, PostgresProduct product, int quantity) {
        cartItemRepository.save(new CartItem(user, product, quantity));
    }

    public void deleteCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartByUser(User user) {
        return cartItemRepository.findByUser(user);
    }


}
