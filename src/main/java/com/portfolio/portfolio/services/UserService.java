package com.portfolio.portfolio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.portfolio.Entities.adminEntity;
import com.portfolio.portfolio.dao.UserRepository;

@Service
public class UserService {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        
        adminEntity user = new adminEntity();

        user.setEmail(username);
        user.setPassword(encodedPassword);
        user.setRole("ROLE_ADMIN");

        userRepository.save(user);

        System.out.println("Successfull save username and password.......Successfull save username and password......................................................");

        System.out.println("Email: " + username);
        System.out.println("Encoded Password: " + encodedPassword);
    }

    
}
