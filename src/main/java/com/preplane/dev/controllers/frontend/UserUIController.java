package com.preplane.dev.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserUIController {
    @RequestMapping("/profile")
    public String profilePage() {
        return "profile/profile";
    }
}
