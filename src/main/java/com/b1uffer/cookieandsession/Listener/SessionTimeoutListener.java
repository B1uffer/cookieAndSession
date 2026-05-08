package com.b1uffer.cookieandsession.Listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SessionTimeoutListener implements HttpSessionListener {
    private static final Logger log = LoggerFactory.getLogger(SessionTimeoutListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("세션 생성됨 : {}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("세션 만료됨 : {}", se.getSession().getId());
        // 만료 메서드에서 DB 로그 기록, 알림 전송 등을 수행할 수 있다
    }
}
