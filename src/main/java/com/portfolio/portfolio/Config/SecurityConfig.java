package com.portfolio.portfolio.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService getuserDetailsService(){

        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getuserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }


    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/adminAccess/**").hasRole("ADMIN")
                .requestMatchers("/adminAccess/**").hasRole("USER")
                .requestMatchers("/**").permitAll())
            .formLogin(form -> form
                .loginPage("/admin")
                .loginProcessingUrl("/dologin")
                .defaultSuccessUrl("/adminAccess/dashboard")
            )

            .logout(logout -> logout

             .logoutUrl("/perform-logout")
             .logoutSuccessUrl("/admin")
             .invalidateHttpSession(true)  // Invalidate session on logout
            .deleteCookies("JSESSIONID")  // Delete session cookies
            .permitAll()

            )
            
            .authenticationProvider(authenticationProvider());
            

        return http.build();
    }
}
