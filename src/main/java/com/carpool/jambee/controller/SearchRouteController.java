package com.carpool.jambee.controller;

import com.carpool.jambee.controller.abstracts.AbstractRouteController;
import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.UserData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchRouteController extends AbstractRouteController {

    @GetMapping("/search")
    public ModelAndView search(ModelAndView modelAndView) {
        modelAndView.setViewName("search");
        return modelAndView;
    }

    @PostMapping("/search/results")
    public ModelAndView produceSearchResults(
        ModelAndView modelAndView
    ) {
        modelAndView.setViewName("search_results");
        return modelAndView;
    };

    // need to get input from user input from add page
    @GetMapping("search/test")
    public List<UserData> searchSimilarRoutes(String startingCity, String destinationCity, String startingStateID,
                                              String destinationStateID, Double startingRadius, Double destinationRadius){
        List<Address> startingAddress = findByProximity(startingCity, startingStateID, startingRadius);
        List<Address> destinationAddress = findByProximity(destinationCity, destinationStateID, destinationRadius);
//        List<Address> startingAddress = findByProximity("Pomona", "CA", 60.0);
//        List<Address> destinationAddress = findByProximity("Temecula", "CA", 60.0);

        List<UserData> userWithSameStartingAddress = this.userDataRepository.findByStartingAddressIn(startingAddress);
        List<UserData> userWithSameDestinationAddress = this.userDataRepository.findByDestinationAddressIn(destinationAddress);

        // Retain all does intersection so only users that have same starting address and destination address
        // should be in the list
        userWithSameStartingAddress.retainAll(userWithSameDestinationAddress);

        return userWithSameStartingAddress;
    }
}
