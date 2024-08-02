package com.portfolio.portfolio.Config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import com.portfolio.portfolio.Entities.adminEntity;
import com.portfolio.portfolio.dao.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
       // fetching user from database
       adminEntity user = userRepository.getUserByUserName(username);

        //if admin user not found  

        if(user==null){
            throw new UsernameNotFoundException("User not found with username : "+ username);
            
            
        }


        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        return customUserDetails;
    }


    
}
