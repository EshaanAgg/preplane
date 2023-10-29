package com.preplane.dev.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OtherControllers {

    @GetMapping("/blog")
    public String discuss() {
        return "blog/discuss";
    }

    @GetMapping("/problems")
    public String problems() {
        return "problems/problems";
    }

    @GetMapping("/premium")
    public String premium() {
        return "premium/premium";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "blog/blog";
    }

    @GetMapping("/")
    public String landing() {
        return "landing/landing";
    }

    @PostMapping("/payment/success")
    public String paymentSuccess() {
        return "premium/success";
    }

    @GetMapping("/problems/{id}")
    public String problem(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "problems/problem";
    }

    @GetMapping("/submisson/{id}")
    public String submisson(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "submisson/submisson";
    }

}
