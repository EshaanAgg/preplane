package com.preplane.dev.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeUIController {
    @RequestMapping("/")
    public String homePage() {
        return "home/home";
    }
}