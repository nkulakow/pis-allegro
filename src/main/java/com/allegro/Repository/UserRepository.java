package com.allegro.Repository;

import com.allegro.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}