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
        return "redirect:/home.html";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("message", "hi");
        return "login";
    }

    private String fakeEmail = "email@email.com";
    private String fakePass = "password";

    @PostMapping(value = "/login/submit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> loginHandler(
            @Valid @RequestBody User loginData) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (loginData.getEmail().equals(fakeEmail) && loginData.getPassword().equals(fakePass))
            result.setMsg("/home");
        else
            result.setMsg("/login?error");

        return ResponseEntity.ok(result);
    }

    /*
    @PostMapping(value = "/login/submit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> loginHandler(
                //@Valid @RequestBody User loginData,
                User loginData,
                Model model ) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (loginData.getEmail().equals("")) {
            return ResponseEntity.ok("Email cannot be empty.");
        }

        if (loginData.getEmail().equals("testme@gmail.com") && loginData.getPassword().equals("password")) {
            result.setMsg("Exists!");
        }
        else {
            result.setMsg("Email or password is incorrect.");
        }

        return ResponseEntity.ok(result);
    }*/
}
