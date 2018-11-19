package com.carpool.jambee.controller;

import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.UserData;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewRouteController {
    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @PostMapping(value="/add",
                 consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addNewRoute(
            Model model,
            UserData userData,
            String startingCity,
            String startingState,
            String destinationCity,
            String destinationState,
            String day1, String day2, String day3,
            String day4, String day5, String day6, String day7)
    {
        boolean daysOfWeek[] = {
                day1 != null,
                day2 != null,
                day3 != null,
                day4 != null,
                day5 != null,
                day6 != null,
                day7 != null
        };
        Address startingAddress = new Address(startingCity, startingState);
        Address destinationAddress = new Address(destinationCity, destinationState);

        userData.setDaysOfWeek(daysOfWeek);
        userData.setStartingAddress(startingAddress);
        userData.setDestinationAddress(destinationAddress);

        if (!userData.getStartingAddress().getCity().equals(""))
            model.addAttribute("cityData", userData.getStartingAddress().getCity());

        return "redirect:/add";
    }

}
