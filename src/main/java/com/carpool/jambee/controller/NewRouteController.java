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
import java.util.List;

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
            int    startingCityRadius,
            String destinationCity,
            String destinationState,
            int    destinationCityRadius,
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

        class CityNames {
            public String starting = "";
            public String destination = "";
        }

        ArrayList<CityNames> cityNames = new ArrayList<>();

        if (startCityExists && destinationCityExists &&
            startingCityRadius >= 0 && startingCityRadius <= 80 &&
            destinationCityRadius >= 0 && destinationCityRadius <= 80)
        {
            mainMessage = "Submission success.";
            List<Address> startingCities = findByProximity(
                startingCity, startingState,
                milesToKM(startingCityRadius));
            List<Address> destinationCities = findByProximity(
                destinationCity, destinationState,
                milesToKM(destinationCityRadius));

            for (Address city : startingCities) {
                CityNames temp = new CityNames();
                temp.starting = city.getCity();
                cityNames.add(temp);
            }
            for (int i = 0; i < destinationCities.size(); i++) {
                CityNames temp = new CityNames();
                temp.destination = destinationCities.get(i).getCity();
                if (i < startingCities.size())
                    cityNames.get(i).destination = temp.destination;
                else
                    cityNames.add(temp);
            }

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
            if (startingCityRadius < 0)
                messages.add("Starting city radius cannot be less than 0 miles");
            if (startingCityRadius > 80)
                messages.add("Starting city radius cannot be more than 80 miles");
            if (destinationCityRadius < 0)
                messages.add("Destination city radius cannot be less than 0 miles");
            if (destinationCityRadius > 80)
                messages.add("Destination city radius cannot be more than 80 miles");

        }

        modelAndView.addObject("isSubmit", true);
        modelAndView.addObject("mainMessage", mainMessage);
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("citiesNames", cityNames);

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
