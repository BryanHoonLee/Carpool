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
            //@Valid NewUser newUser) {
            NewUser newUser) {
        boolean createNewUser = true;

        System.out.println(newUser.getFirstName());
        System.out.println(newUser.getLastName());
        System.out.println(newUser.getEmail());
        System.out.println(newUser.getPassword());
        System.out.println(newUser.getMatchingPassword());

        String redirectLocation = "register_complete";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (newUser.getPassword().equals(newUser.getMatchingPassword())) {
            model.addAttribute("registerError", true);
            model.addAttribute("mismatchedPasswords", true);
            redirectLocation = "register";
            createNewUser = false;
        }

        if (!isValidEmailAddress(newUser.getEmail())) {
            model.addAttribute("registerError", true);
            model.addAttribute("notValidEmail", true);
            redirectLocation = "register";
            createNewUser = false;
        }
        else {
            if (!isEmailEduAddress(newUser.getEmail())) {
                model.addAttribute("registerError", true);
                model.addAttribute("notEduEmail", true);
                redirectLocation = "register";
                createNewUser = false;
            }
        }

        if(userDataRepository.findByEmailEquals(newUser.getEmail()) != null && userDataRepository.findByEmailEquals(newUser.getEmail()).equals(newUser.getEmail())){
            model.addAttribute("registerError", true);
            model.addAttribute("notValidEmail", true);
            redirectLocation = "register";
            createNewUser = false;
        }

        if(createNewUser){
            UserData userData = new UserData(newUser.getFirstName(), newUser.getLastName(),
                    newUser.getPassword(), null, null, null,
                    newUser.getEmail(), null, null, null);

            userDataRepository.insert(userData);
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
