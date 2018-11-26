package com.carpool.jambee.controller;

import com.carpool.jambee.controller.abstracts.AbstractRouteController;
import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.security.UserStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
public class NewRouteController extends AbstractRouteController {
    @GetMapping("/add")
    public ModelAndView add(
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("add");
        return modelAndView;
    }

    @PostMapping(value="/add",
                 consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView addNewRoute(
            ModelAndView modelAndView,
            UserData userData,
            String startingCity,
            String startingState,
            String destinationCity,
            String destinationState,
            String day1, String day2, String day3,
            String day4, String day5, String day6, String day7)
    {
        Address startingAddress = new Address(startingCity, startingState);
        Address destinationAddress = new Address(destinationCity, destinationState);

        boolean startCityExists, destinationCityExists;
        startCityExists = checkExistsCityAndStateID(startingCity, startingState);
        destinationCityExists = checkExistsCityAndStateID(destinationCity, destinationState);

        String mainMessage = "";
        ArrayList<String> messages = new ArrayList<>();

        if (startCityExists && destinationCityExists &&
            userData.getEmail() != null)
        {
            mainMessage = "Submission success.";

            userData.setId(new UserStatus().getUserId());
            userData.setDaysOfWeek(handleDaysOfWeek(day1, day2, day3, day4, day5 ,day6, day7));
            userData.setStartingAddress(startingAddress);
            userData.setDestinationAddress(destinationAddress);

            if (this.userDataRepository.findByIdEquals(userData.getId()) != null)
                this.userDataRepository.delete(this.userDataRepository.findByIdEquals(userData.getId()));

            this.userDataRepository.insert(userData);
        }
        else {
            mainMessage = "Failed to submit:";

            if (!startCityExists)
                messages.add("Starting city does not exist.");
            if (!destinationCityExists)
                messages.add("Destination city does not exist.");
            if (userData == null)
                messages.add("Email contact information cannot be blank.");
        }

        modelAndView.addObject("isSubmit", true);
        modelAndView.addObject("mainMessage", mainMessage);
        modelAndView.addObject("messages", messages);

        modelAndView.setViewName("add");

        return modelAndView;
    }

    private boolean[] handleDaysOfWeek(
        String day1, String day2, String day3, String day4,
        String day5, String day6, String day7
    ) {
        boolean daysOfWeek[] = {
            day1 != null, day2 != null, day3 != null, day4 != null,
            day5 != null, day6 != null, day7 != null
        };

        return daysOfWeek;
    }
}
