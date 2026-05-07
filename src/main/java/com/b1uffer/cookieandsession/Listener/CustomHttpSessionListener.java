package com.b1uffer.cookieandsession.Listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomHttpSessionListener implements HttpSessionListener {
    private static final Logger log = LoggerFactory.getLogger(CustomHttpSessionListener.class);

    // 세션의 생성
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("[세션 생성] sessionId = {} 생성됨", se.getSession().getId());
    }

    // 세션의 소멸
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("[세션 소멸] sessionId = {} 소멸됨", se.getSession().getId());
    }
}
