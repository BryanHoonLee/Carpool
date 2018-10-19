package com.carpool.jambee.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserLoginAndRegisterController {

    @GetMapping("/login")
    public String login() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails)
            return "redirect:/home";

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails)
            return "redirect:/home";

        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerHandler(ModelAndView modelAndView) {
        modelAndView.addObject("registered", true);
        modelAndView.setViewName("register");
        return modelAndView;
    }
}
