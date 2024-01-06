package com.allegro.Repository;

import com.allegro.Entity.CartItem;
import com.allegro.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
    CartItem findByUser(User user);
}
