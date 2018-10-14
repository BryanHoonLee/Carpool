package com.carpool.jambee.controller;

import com.carpool.jambee.model.AjaxResponseBody;
import com.carpool.jambee.model.LoginData;
import com.carpool.jambee.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/home.html";
    }

    @PostMapping("/login/submit-data")
    public ResponseEntity<?> loginHandler(
                @Valid @RequestBody User loginData,
                Error error ) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (loginData.getEmail().equals("testme@gmail.com") && loginData.getPassword().equals("password")) {
            result.setMsg("Exists!");
        }
        else {
            result.setMsg("Email or password is incorrect");
        }

        return ResponseEntity.ok(result);
    }
}
