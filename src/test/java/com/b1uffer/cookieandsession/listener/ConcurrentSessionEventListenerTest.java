package com.b1uffer.cookieandsession.listener;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConcurrentSessionEventListenerTest {
    @Autowired
    ApplicationEventPublisher publisher;

    @Test
    void 세션_만료_이벤트_테스트() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                securityContext("user")
        );

        HttpSessionDestroyedEvent event = new HttpSessionDestroyedEvent(session);

        publisher.publishEvent(event);
    }

    private SecurityContext securityContext(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );

        context.setAuthentication(authentication);
        return context;
    }
}
