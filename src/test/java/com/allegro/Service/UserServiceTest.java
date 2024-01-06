package com.allegro.Service;

import com.allegro.Entity.CartItem;
import com.allegro.Entity.PostgresProduct;
import com.allegro.Entity.User;
import com.allegro.Repository.CartItemRepository;
import com.allegro.Repository.PostgresProductRepository;
import com.allegro.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private PostgresProductRepository productRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void SetUp(){
        userRepository = mock(UserRepository.class);
        cartItemRepository = mock(CartItemRepository.class);
        productRepository = mock(PostgresProductRepository.class);
        userService = new UserService(userRepository, cartItemRepository, productRepository);
    }

    @Test
    void findUserByEmailTest() {
        String email1 = "abc@gmail.com";
        String email2 = "def@gmail.com";
        var user1 = new User(email1, "abc", "abc", "abc", null, null);
        var user2 = new User(email2, "def", "def", "def", null, null);

        when(userRepository.findByEmail(email1)).thenReturn(user1);
        when(userRepository.findByEmail(email2)).thenReturn(user2);

        User resultUser = userService.getUserByEmail(email1);
        assertEquals(user1, resultUser);
        verify(userRepository, times(1)).findByEmail(email1);

        resultUser = userService.getUserByEmail(email2);
        assertEquals(user2, resultUser);
    }

    @Test
    void getAllUsersTest() {
        String email1 = "abc@gmail.com";
        String email2 = "def@gmail.com";
        var user1 = new User(email1, "abc", "abc", "abc", null, null);
        var user2 = new User(email2, "def", "def", "def", null, null);

        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> resultUsers = userService.getAllUsers();

        assertEquals(users, resultUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void addUserTestwUserClass() {
        String email1 = "abc@gmail.com";
        var user1 = new User(email1, "abc", "abc", "abc", null, null);

        when(userRepository.save(user1)).thenReturn(user1);

        userService.addUser(user1);
        verify(userRepository, times(1)).save(user1);
    }
    @Test
    void addUserTestwParams() {
        var user1 = new User("abc@gmail.com", "abc", "abc", "abc", null, null);

        when(userRepository.save(any(User.class))).thenReturn(user1);

        userService.addUser("abc@gmail.com", "abc", "abc", "abc", null, null);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void loginTest() {
        String email1 = "abc@gmail.com";
        var user1 = new User(email1, "abc", "abc", "abc", null, null);

        when(userRepository.findByEmail(email1)).thenReturn(user1);

        var validResult = userService.login(email1, "abc");
        assertTrue(validResult);
        var invalidResult = userService.login(email1, "def");
        assertFalse(invalidResult);
    }

    @Test
    void noUserLoginTest() {
        String email1 = "abc@gmail.com";

        when(userRepository.findByEmail(email1)).thenReturn(null);

        var invalidResult = userService.login(email1, "def");
        assertFalse(invalidResult);
    }

    @Test
    void addCartItemTest() {
        User user = new User("test@example.com", "John", "Doe", "password", null, null);
        CartItem cartItem = new CartItem(user, new PostgresProduct(), 2);

        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.addCartItem(user, cartItem);

        verify(userRepository, times(1)).save(user);
        verify(cartItemRepository, times(1)).save(cartItem);

        assertTrue(user.getCartItems().contains(cartItem));
    }

    @Test
    void buyCartItemsTest() {
        User user = new User("test@example.com", "John", "Doe", "password", null, null);
        PostgresProduct product = new PostgresProduct();
        CartItem cartItem = new CartItem(user, product, 2);
        user.addCartItem(cartItem);

        doNothing().when(cartItemRepository).delete(cartItem);
        when(productRepository.save(any(PostgresProduct.class))).thenReturn(product);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.buyCartItems(user);


        verify(cartItemRepository, times(1)).delete(cartItem);
        verify(productRepository, times(1)).save(product);
        verify(userRepository, times(1)).save(user);

        assertFalse(user.getCartItems().contains(cartItem));
    }

}
