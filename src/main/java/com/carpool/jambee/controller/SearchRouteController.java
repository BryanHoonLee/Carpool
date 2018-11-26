package com.carpool.jambee.controller;

import com.carpool.jambee.controller.abstracts.AbstractRouteController;
import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.UserData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchRouteController extends AbstractRouteController {

    class UserDataTemp {
        public String from;
        public String to;
        public String days;
        public String email;
        public String number;
        public String compensation;
        public String notes;
    };

    @GetMapping("/search")
    public ModelAndView search(ModelAndView modelAndView) {
        modelAndView.setViewName("search");
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView produceSearchResults(
        ModelAndView modelAndView,
        String startingCity,
        String startingState,
        int    startingCityRadius,
        String destinationCity,
        String destinationState,
        int    destinationCityRadius,
        String day1, String day2, String day3, String day4,
        String day5, String day6, String day7
    ) {

        ArrayList<String> messages = new ArrayList<>();
        ArrayList<UserDataTemp> userDataList = new ArrayList<>();
        boolean searchResultsAvailable = false;
        boolean isInputValid = checkIfInputIsValid( messages,
                startingCity, startingState, startingCityRadius,
                destinationCity, destinationState, destinationCityRadius);
        ArrayList<UserData> foundUsers = (ArrayList<UserData>) searchSimilarRoutes(
                startingCity, destinationCity,
                startingState, destinationState,
                milesToKM(startingCityRadius), milesToKM(destinationCityRadius));

        if (!foundUsers.isEmpty()) {
            searchResultsAvailable = true;
            for (UserData data : foundUsers) {
                UserDataTemp newData = interpretUserData(data);
                userDataList.add(newData);
            }
        }

        if (isInputValid) {
            if (searchResultsAvailable)
                messages.add("Found routes! Scroll below to see the list.");
            else {
                messages.add("Unable to find any routes with your search parameters.");
                messages.add("Try reversing the starting and destination cities to see if it helps.");
                messages.add("You can also try increasing the radii around the cities to include more cities.");
            }
        }

        modelAndView.addObject("messages", messages);
        modelAndView.addObject("isSubmit", true);
        modelAndView.addObject("searchResultsAvailable", searchResultsAvailable);
        modelAndView.addObject("userDataList", userDataList);

        modelAndView.setViewName("search");
        return modelAndView;
    };

    private UserDataTemp interpretUserData(UserData data) {
        UserDataTemp newData = new UserDataTemp();

        newData.from = data.getStartingAddress().getCity();
        newData.from += ", " + data.getStartingAddress().getStateID();
        newData.to = data.getDestinationAddress().getCity();
        newData.to += ", " + data.getDestinationAddress().getStateID();
        newData.days = interpretDaysOfWeek(data.getDaysOfWeek());
        newData.email = data.getEmail();
        newData.compensation = data.getPreferredCompensation();

        if (!data.getPhoneNumber().equals(""))
            newData.number = data.getPhoneNumber();
        else
            newData.number = "None provided";

        if (!data.getAdditionalNotes().equals(""))
            newData.notes = data.getAdditionalNotes();
        else
            newData.notes = "No additional notes.";

        return newData;
    }

    private List<UserData> searchSimilarRoutes(String startingCity, String destinationCity, String startingStateID,
                                              String destinationStateID, int startingRadius, int destinationRadius) {
        List<Address> startingAddress = findByProximity(startingCity, startingStateID, startingRadius);
        List<Address> destinationAddress = findByProximity(destinationCity, destinationStateID, destinationRadius);

        List<UserData> userWithSameStartingAddress = this.userDataRepository.findByStartingAddressIn(startingAddress);
        List<UserData> userWithSameDestinationAddress = this.userDataRepository.findByDestinationAddressIn(destinationAddress);

        // Retain all does intersection so only users that have same starting address and destination address
        // should be in the list
        userWithSameStartingAddress.retainAll(userWithSameDestinationAddress);

        return userWithSameStartingAddress;
    }
}
