package com.b1uffer.cookieandsession.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/me")
    public String me() {
        // SecurityContextHolder는 Authentication 정보를 저장하고 조회하는 기능
        // 실제로는 SecurityContext에 저장된다
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "현재 사용자는 " + authentication.getName() + "이고, 권한은 " + authentication.getAuthorities();
    }
}
