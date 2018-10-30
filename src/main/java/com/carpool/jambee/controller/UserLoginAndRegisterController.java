package com.carpool.jambee.controller;

import com.carpool.jambee.model.NewUser;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @PostMapping(value = "/register/submit",
                 consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String registerHandler(
            Model model,
            @Valid NewUser newUser) {

        System.out.println(newUser.getFirstName());
        System.out.println(newUser.getLastName());
        System.out.println(newUser.getEmail());
        System.out.println(newUser.getPassword());
        System.out.println(newUser.getMatchingPassword());

        //String redirectLocation = "confirm";
        String redirectLocation = "redirect:/home";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (newUser.getPassword().equals(newUser.getMatchingPassword())) {
            model.addAttribute("mistmatchedPasswords", true);
            redirectLocation = "register";
        }

        return redirectLocation;
    }
}
