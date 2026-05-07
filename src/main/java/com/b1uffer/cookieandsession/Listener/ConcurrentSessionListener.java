package com.b1uffer.cookieandsession.Listener;

import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class ConcurrentSessionListener {
    @EventListener
    public void onSessionDestroyed(HttpSessionDestroyedEvent event) {
        String sessionId = event.getSession().getId();
        System.out.println("[ConcurrentSessionFilter] 세션 만료 감지 - ID : " + sessionId);
        event.getSecurityContexts().forEach(context -> {
            System.out.println("만료된 사용자 : " + context.getAuthentication().getName());
        });
    }
}
