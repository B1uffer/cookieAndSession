package com.b1uffer.cookieandsession.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    @Bean
    @Order(1)
    public SecurityFilterChain meFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/me/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        //세션 생성 정책 설정하기
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // REST API, JWT일 때

                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession)
                        .maximumSessions(1) // 최대 1개의 세션만 허용하기
                        .expiredUrl("/session-expired")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/me/**").authenticated()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}pass")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}pass")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
