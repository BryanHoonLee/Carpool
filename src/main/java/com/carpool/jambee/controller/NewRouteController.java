package com.carpool.jambee.controller;

import com.carpool.jambee.CityRouteMath;
import com.carpool.jambee.LatLngArea;
import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.CityData;
import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.mongodb.repository.CityDataRepository;
import com.carpool.jambee.mongodb.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class NewRouteController {
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private CityDataRepository cityDataRepository;

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @PostMapping("/test")
    public boolean testOnly(String city) {
        System.out.println(city);
        return checkExistsCityAndStateID(city, "CA");
        //return "/test";
    }

    @PostMapping(value="/add",
                 consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addNewRoute(
            Model model,
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

        boolean startCityExists, destinationCityExists;
        startCityExists = checkExistsCityAndStateID(startingCity, startingState);
        destinationCityExists = checkExistsCityAndStateID(destinationCity, destinationState);

        ArrayList<String> messages = new ArrayList<>();
        String mainMessage = "";

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
                startingCity,
                startingState,
                milesToKM(startingCityRadius));
            List<Address> destinationCities = findByProximity(
                destinationCity,
                destinationState,
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

        userData.setDaysOfWeek(daysOfWeek);
        userData.setStartingAddress(startingAddress);
        userData.setDestinationAddress(destinationAddress);

        // Maybe need to add rest of data to userData like email, phone #
        this.userDataRepository.insert(userData);

        model.addAttribute("isSubmit", true);
        model.addAttribute("mainMessage", mainMessage);
        model.addAttribute("messages", messages);
        model.addAttribute("citiesNames", cityNames);

        return "/add";
    }

    private int milesToKM(int miles) {
        return (int)((double)miles * 1.609344);
    }

    private int kmToMiles(int km) {
        return (int)((double)km * 0.6213712);
    }

    // Check if there exists a city with specified City Name and State ID
    private boolean checkExistsCityAndStateID(String city, String stateID) {
        List<CityData> cityList = this.cityDataRepository.findByStateIDAndCity(stateID, city);
        boolean cityAndStateIDExists = false;
        for(int i = 0; i < cityList.size(); i++){
            if (cityList.get(i).getCity().equals(city) && cityList.get(i).getStateID().equals(stateID)){
                cityAndStateIDExists = true;
            }
        }
        return cityAndStateIDExists;
    }

    // Finds all cities in proximity in same state
    public List<Address> findByProximity(String city, String stateID, double radius) {
        if (radius <= 0) radius = 1;

        List<CityData> cityWithSameStateID = this.cityDataRepository.findByStateID(stateID);
        CityData originCity = new CityData();
        for(CityData cityData: cityWithSameStateID) {
            if (cityData.getStateID().equals(stateID) && cityData.getCity().equals(city)) {
                originCity = cityData;
            }
        }

        List<Address> foundCities = new ArrayList<>();
        CityRouteMath cityRouteMath = new CityRouteMath();
        LatLngArea originCityArea = cityRouteMath.getLatLngArea(originCity.getLat(), originCity.getLng(),radius);

        for(int i = 0; i < cityWithSameStateID.size(); i++){
            if(cityWithSameStateID.get(i).getLat() < originCityArea.getLat1() &&
               cityWithSameStateID.get(i).getLat() > originCityArea.getLat3() &&
               cityWithSameStateID.get(i).getLng() < originCityArea.getLng1() &&
               cityWithSameStateID.get(i).getLng() > originCityArea.getLng2())
            {
                foundCities.add(new Address(cityWithSameStateID.get(i).getCity(), cityWithSameStateID.get(i).getStateID()));
            }
        }

        return foundCities;
    }

    // need to get input from user input from add page
    @GetMapping("search/test")
    public List<UserData> searchSimilarRoutes(String startingCity, String destinationCity, String startingStateID,
                                              String destinationStateID){
//        List<Address> startingAddress = findByProximity(startingCity, startingStateID, startingRadius);
//        List<Address> destinationAddress = findByProximity(destinationCity, destinationStateID, destinationRadius);
        List<Address> startingAddress = findByProximity("Pomona", "CA", 20.0);
        List<Address> destinationAddress = findByProximity("Temecula", "CA", 20.0);

        List<UserData> userWithSameStartingAddress = this.userDataRepository.findByStartingAddressIn(startingAddress);
        List<UserData> userWithSameDestinationAddress = this.userDataRepository.findByDestinationAddressIn(destinationAddress);

        // Retain all does intersection so only users that have same starting address and destination address
        // should be in the list
        //userWithSameStartingAddress.retainAll(userWithSameDestinationAddress);

        return userWithSameDestinationAddress;
    }

}
