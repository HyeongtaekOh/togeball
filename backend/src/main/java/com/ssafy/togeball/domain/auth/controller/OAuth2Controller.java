package com.ssafy.togeball.domain.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/social")
public class OAuth2Controller {
    @RequestMapping("/login")
    public String loginForm() {
        return "test/loginForm";
    }

    @RequestMapping("/after")
    public String afterLogin() {
        return "test/afterLogin";
    }

    @GetMapping("/oauth2/authorize/{provider}")
    public RedirectView redirectToOAuth2Provider(@PathVariable(name = "provider") String provider) {
        System.out.println("hey hey!");
        String url = "http://localhost:8080/oauth2/authorization/" + provider;
        return new RedirectView(url);
    }
}
