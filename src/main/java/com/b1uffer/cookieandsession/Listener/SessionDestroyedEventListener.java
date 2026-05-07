package com.b1uffer.cookieandsession.Listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

/**
 * SecuritySessionEventListener 랑 둘중에 선택해서 사용하면 됨
 */
@Component
public class SessionDestroyedEventListener implements ApplicationListener<HttpSessionDestroyedEvent> {
    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent event) {
        event.getSecurityContexts().forEach(content -> {
            String username = content.getAuthentication().getName();
            System.out.println("[세션 만료 감지] 사용자 : " + username);
        });
    }
}
