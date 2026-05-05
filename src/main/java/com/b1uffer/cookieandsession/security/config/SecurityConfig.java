package com.b1uffer.cookieandsession.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    @Order(0)
    public SecurityFilterChain h2FilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(PathRequest.toH2Console())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf ->csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()))
                .formLogin(login -> login
                        .loginPage("/login").permitAll());
        return http.build();
    }
}
