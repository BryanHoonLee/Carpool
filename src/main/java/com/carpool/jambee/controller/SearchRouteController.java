package com.carpool.jambee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchRouteController {

    @GetMapping("/search")
    public ModelAndView search(ModelAndView modelAndView) {
        modelAndView.setViewName("search");
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView searchResults(
        ModelAndView modelAndView
    ) {
        modelAndView.setViewName("/search/results");
        return modelAndView;
    };
}
