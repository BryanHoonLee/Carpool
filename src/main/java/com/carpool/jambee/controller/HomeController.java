package com.carpool.jambee.controller;

import com.carpool.jambee.security.UserStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping({"/", "/index", "/home"})
    public ModelAndView index(ModelAndView modelAndView) {
        UserStatus userStatus = new UserStatus();

        modelAndView.addObject("isSignedIn", userStatus.isUserLoggedIn());
        modelAndView.setViewName("index");

        return modelAndView;
    }

}
