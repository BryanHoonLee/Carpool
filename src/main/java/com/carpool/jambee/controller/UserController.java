package com.carpool.jambee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @GetMapping("/profile")
    public ModelAndView userProfile(ModelAndView modelAndView) {
        modelAndView.setViewName("user_settings");
        return modelAndView;
    }
}
