package com.portfolio.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.portfolio.portfolio.services.UserService;

@SpringBootApplication
public class PortfolioApplication{

	@Autowired
    private UserService userService;
 
	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}

	// @Override
    // public void run(String... args) throws Exception {
    //     String username = "majidnaseem720@gmail.com";
    //     String password = "majid123@";
    //     userService.registerUser(username, password);
    // }
    

}