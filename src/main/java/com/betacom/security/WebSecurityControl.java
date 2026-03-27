package com.betacom.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityControl {


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .cors(Customizer.withDefaults()) 
	        .csrf(csrf -> csrf.disable()) 
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/**").permitAll()
	            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
	           // .requestMatchers("/rest/**").hasRole("ADMIN")
	            .anyRequest().authenticated()
	        )
	        //.httpBasic(Customizer.withDefaults()) 
	        .logout(logout -> logout
	            .logoutUrl("/rest/user/logout")
	            .invalidateHttpSession(true) 
	            .clearAuthentication(true)       
	            .deleteCookies("JSESSIONID")   
	            .logoutSuccessHandler((request, response, authentication) -> {
	                response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().write("Logout effettuato con successo");
	            })
	        );

	    return http.build();
	}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}