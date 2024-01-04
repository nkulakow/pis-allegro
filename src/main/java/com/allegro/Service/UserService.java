package com.allegro.Service;

import com.allegro.Entity.CartItem;
import com.allegro.Entity.PostgresProduct;
import com.allegro.Entity.User;
import com.allegro.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void addUser(String email, String password, String name, String surname, ArrayList<PostgresProduct> soldProducts, ArrayList<CartItem> cartItems ){
        userRepository.save(new User(email, password, name, surname, soldProducts, cartItems));
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User getUserById(String id){
        var user = userRepository.findById(id);
        return user.orElse(null);
    }

    public boolean login(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user == null){
            return false;
        }
        User toCompare = new User(email, password, "toCompare", "toCompare", new ArrayList<>(), new ArrayList<>());
        return user.getPasswordHash().equals(toCompare.getPasswordHash());
    }
}
