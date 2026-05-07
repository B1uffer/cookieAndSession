package com.b1uffer.cookieandsession.Listener;

import org.springframework.context.event.EventListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;


/**
 * SessionDestroyedEventListener 랑 둘중에 선택해서 사용하면 됨
 */
@Component
public class SecuritySessionEventListener {

    @EventListener
    public void handleSessionDestroyed(SessionDestroyedEvent event) {
        event.getSecurityContexts().forEach(context -> {
            System.out.println("[세션 만료] 사용자 : " + context.getAuthentication().getName());
        });
    }
}
