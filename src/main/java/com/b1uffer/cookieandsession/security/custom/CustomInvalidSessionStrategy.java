package com.b1uffer.cookieandsession.security.custom;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {


    @Override
    public void onInvalidSessionDetected(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException, ServletException {
        // REST API 응답 예
        response.setContentType("application/json; charset=UTF-8"); // 응답 타입 정해주기
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.getWriter().write("{\"error\": \"세션이 만료되었습니다. 다시 로그인해주세요.\"}");
        response.getWriter().flush(); // flush로 한번에 보내기
    }
}
