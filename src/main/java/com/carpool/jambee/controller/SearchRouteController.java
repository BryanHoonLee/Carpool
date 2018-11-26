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

    /*
                startingCityRadius >= 0 && startingCityRadius <= 80 &&
                    destinationCityRadius >= 0 && destinationCityRadius <= 80

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




if (startingCityRadius < 0)
                messages.add("Starting city radius cannot be less than 0 miles");
            if (startingCityRadius > 80)
                messages.add("Starting city radius cannot be more than 80 miles");
            if (destinationCityRadius < 0)
                messages.add("Destination city radius cannot be less than 0 miles");
            if (destinationCityRadius > 80)
                messages.add("Destination city radius cannot be more than 80 miles");


                modelAndView.addObject("citiesNames", cityNames);
     */


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
