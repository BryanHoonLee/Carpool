package com.carpool.jambee.controller;

import com.carpool.jambee.model.NewUser;
import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.mongodb.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Pattern;

@Controller
public class UserLoginAndRegisterController {

    @Autowired
    private UserDataRepository userDataRepository;

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

    @PostMapping(value = "/register",
                 consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String registerHandler(
            Model model,
            NewUser newUser) {

        boolean createNewUser = true;
        String redirectLocation = "index";

        if (!newUser.doPasswordsMatch()) {
            model.addAttribute("registerError", true);
            model.addAttribute("mismatchedPasswords", true);
            redirectLocation = "register";
            createNewUser = false;
        }

        if (!isValidEmailAddress(newUser.getSignupEmail())) {
            model.addAttribute("registerError", true);
            model.addAttribute("notValidEmail", true);
            redirectLocation = "register";
            createNewUser = false;
        }
        else {
            if (!isEmailEduAddress(newUser.getSignupEmail())) {
                model.addAttribute("registerError", true);
                model.addAttribute("notEduEmail", true);
                redirectLocation = "register";
                createNewUser = false;
            }
        }

        if(userDataRepository.findBySignupEmailEquals(newUser.getSignupEmail()) != null &&
           userDataRepository.findBySignupEmailEquals(newUser.getSignupEmail()).getSignupEmail().equals(newUser.getSignupEmail()))
        {
            model.addAttribute("registerError", true);
            model.addAttribute("alreadyRegisteredEmail", true);
            redirectLocation = "register";
            createNewUser = false;
        }

        if(createNewUser) {
            UserData userData = new UserData(
                newUser.getFirstName(), newUser.getLastName(),
                newUser.getSignupEmail(), newUser.getPassword(),
                null, null, null, null, null, null, null);

            userDataRepository.insert(userData);

            model.addAttribute("justRegistered", true);
        }

        return redirectLocation;
    }

    private boolean isValidEmailAddress(String str) {
        if (str.length() < 3) return false;

        return Pattern.matches("\\S+@\\S+", str);
    }

    private boolean isEmailEduAddress(String address) {
        if (address.length() < 4) return false;

        return Pattern.matches("\\S*@\\S+.edu", address);
    }
}
