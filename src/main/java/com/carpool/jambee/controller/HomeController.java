package com.carpool.jambee.controller;

import com.carpool.jambee.controller.abstracts.AbstractRouteController;
import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.security.UserStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends AbstractRouteController {

    @GetMapping({"/", "/index", "/home"})
    public ModelAndView index(ModelAndView modelAndView) {
        UserStatus userStatus = new UserStatus();

        modelAndView.addObject("isSignedIn", userStatus.isUserLoggedIn());
        UserData userData = null;
        boolean routeIsSet = false;

        if (userStatus.isUserLoggedIn()) {
            userData = this.userDataRepository.findByIdEquals(userStatus.getUserId());

            if (userData != null)
                routeIsSet = true;
            else
                routeIsSet = false;
        }

        if (routeIsSet) {
            modelAndView.addObject("startingCity", userData.getStartingAddress().getCity());
            modelAndView.addObject("startingStateID", userData.getStartingAddress().getStateID());
            modelAndView.addObject("destinationCity", userData.getDestinationAddress().getCity());
            modelAndView.addObject("destinationStateID", userData.getDestinationAddress().getStateID());
            modelAndView.addObject("days", interpretDaysOfWeek(userData.getDaysOfWeek()));
            modelAndView.addObject("email", userData.getEmail());
            modelAndView.addObject("phoneNumber", userData.getPhoneNumber());
            modelAndView.addObject("compensationPreferred", userData.getPreferredCompensation());
            modelAndView.addObject("additionalNotes", userData.getAdditionalNotes());
        }

        modelAndView.addObject("routeIsSet", routeIsSet);

        modelAndView.setViewName("index");

        return modelAndView;
    }

    private String interpretDaysOfWeek(boolean[] daysOfWeek) {
        String out = "";

        if (daysOfWeek[0]) out += "S";
        else               out += "-";

        if (daysOfWeek[1]) out += "M";
        else               out += "-";

        if (daysOfWeek[2]) out += "T";
        else               out += "-";

        if (daysOfWeek[3]) out += "W";
        else               out += "-";

        if (daysOfWeek[4]) out += "T";
        else               out += "-";

        if (daysOfWeek[5]) out += "F";
        else               out += "-";

        if (daysOfWeek[6]) out += "S";
        else               out += "-";

        return out;
    }

}
