package com.b1uffer.cookieandsession.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {
    @GetMapping("/session-expired")
    public String sessionExpired(Model model) {
        model.addAttribute("msg", "세션이 만료되었습니다. 다시 로그인해주세요");
        return "session-expired";
    }
}
