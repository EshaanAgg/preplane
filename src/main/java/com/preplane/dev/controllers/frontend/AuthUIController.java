package com.preplane.dev.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.preplane.dev.payload.auth.SignUpRequest;


@Controller
public class AuthUIController {

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/signup")
    public String signup(Model model) {
        SignUpRequest user = new SignUpRequest();
        model.addAttribute("user", user);
        return "auth/signup";
    }
}
