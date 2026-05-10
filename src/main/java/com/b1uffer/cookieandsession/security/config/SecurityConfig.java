package com.b1uffer.cookieandsession.security.config;

import com.b1uffer.cookieandsession.security.custom.CustomInvalidSessionStrategy;
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
    private final CustomInvalidSessionStrategy invalidSessionStrategy; // 1. 필드 선언

    public SecurityConfig(CustomInvalidSessionStrategy invalidSessionStrategy) { // 2. 생성자에 주입
        this.invalidSessionStrategy = invalidSessionStrategy;
    }

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

                        // 로그인에 성공했을 때 세션 탈취 방지, 세션 ID를 갈아끼워서 세션 하이재킹 방지
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession)
                        .invalidSessionUrl("/session-expired") // 사용할 수 없는 세션 사용시 이동할 URL, 단순 URL 지정
                        .invalidSessionStrategy(invalidSessionStrategy) // invalidSessionStrategy를 메서드를 통해 주입
                        .maximumSessions(1) // 최대 1개의 세션만 허용하기
                        .maxSessionsPreventsLogin(true) // 새로운 로그인에 대해 차단 여부 설정, 기본 false
                        .expiredUrl("/auths/login-form") // 세션 만료시 이동할 URL, 로그인 다시 하세요
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
