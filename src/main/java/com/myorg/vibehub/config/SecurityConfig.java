package com.myorg.vibehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    //A new blank SecurityFilterChain Object is created.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity
                //Cross Site Request Forgery disabling
                //method chaining
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(

                        auth -> auth
                                //for "/test" endpoint, no auth required.
                               // .requestMatchers("/test").permitAll()
                                .requestMatchers(HttpMethod.GET, "/").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                                .requestMatchers("/api/test/jwt").permitAll()
                                //.requestMatchers(HttpMethod.GET, "/**").permitAll()
                                //for all other http requests auth is required
                                .anyRequest().authenticated()
                )
                //httpBasic enables Basic Auth in your program
                .httpBasic(Customizer.withDefaults());

        //build returns a DefaultSecurityFilterChain
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(daoAuthenticationProvider);
    }
}
