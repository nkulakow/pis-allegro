package com.allegro.Service;

import com.allegro.Entity.User;
import com.allegro.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void SetUp(){
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void findUserByEmailTest() {
        ArrayList<User> users = new ArrayList<>();
        String email1 = "abc@gmail.com";
        String email2 = "def@gmail.com";
        var user1 = new User(email1, "abc", "abc", "abc", null, null);
        var user2 = new User(email2, "def", "def", "def", null, null);
        users.add(user1);
        users.add(user2);

        when(userRepository.findByEmail(email1)).thenReturn(user1);
        when(userRepository.findByEmail(email2)).thenReturn(user2);

        User resultUser = userService.getUserByEmail(email1);
        assertEquals(user1, resultUser);
        verify(userRepository, times(1)).findByEmail(email1);

        resultUser = userService.getUserByEmail(email2);
        assertEquals(user2, resultUser);
    }

}
