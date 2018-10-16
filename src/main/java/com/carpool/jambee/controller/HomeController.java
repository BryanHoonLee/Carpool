package com.carpool.jambee.controller;

import com.carpool.jambee.model.AjaxResponseBody;
import com.carpool.jambee.model.LoginData;
import com.carpool.jambee.model.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class HomeController {

    @GetMapping({"/", "/index", "/home"})
    public String index() {
        return "redirect:/index.html";
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
