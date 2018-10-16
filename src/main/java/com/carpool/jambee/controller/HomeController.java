package com.carpool.jambee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @GetMapping({"/", "/index", "/home"})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("message", "hi");
        return "login";
    }

    @GetMapping("/test")
    public String test() {
        return "test_authentication";
    }
}
