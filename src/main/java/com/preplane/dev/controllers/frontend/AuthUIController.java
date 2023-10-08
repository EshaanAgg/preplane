package com.preplane.dev.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthUIController {

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
