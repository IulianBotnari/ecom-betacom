package com.betacom.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityControl {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    	http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                .requestMatchers("/cart/**", "/checkout/**", "/orders/**", "/profile/**", "/wishlist/**").hasRole("USER")
                .requestMatchers("/", "/shop", "/products", "/product/**", "/login", "/register", "/saveUser").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
        )
        .formLogin((form) -> form
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .permitAll()
        )
        .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        return http.build();
    }
}