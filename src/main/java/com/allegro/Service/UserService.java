package com.allegro.Service;

import com.allegro.Entity.CartItem;
import com.allegro.Entity.PostgresProduct;
import com.allegro.Entity.User;
import com.allegro.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(String email, String password, String name, String surname, List<PostgresProduct> soldProducts, List<CartItem> cartItems ){
        userRepository.save(new User(email, password, name, surname, soldProducts, cartItems));
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean login(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user == null){
            return false;
        }
        return user.getPasswordHash().equals(password);
    }
}
