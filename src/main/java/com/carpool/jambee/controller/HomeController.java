package com.carpool.jambee.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index", "/home"})
    public String index() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println(username);

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
