package com.preplane.dev.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherControllers {

    @GetMapping("/discuss")
    public String discuss() {
        return "discuss/discuss";
    }
    @GetMapping("/problems")
    public String problems() {
        return "problems/problems";
    }
    @GetMapping("/premium")
    public String premium() {
        return "premium/premium";
    }
    @GetMapping("/blog")
    public String blog() {
        return "blog/blog";
    }

    
}
