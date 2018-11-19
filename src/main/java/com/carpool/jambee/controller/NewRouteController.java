package com.carpool.jambee.controller;

import com.carpool.jambee.CityRouteMath;
import com.carpool.jambee.LatLngArea;
import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.CityData;
import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.mongodb.repository.CityDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewRouteController {
    @Autowired
    private CityDataRepository cityDataRepository;

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
        model.addAttribute("submitMessage", true);
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

        boolean success;
        success = checkExistsCityAndStateID(startingCity, startingState) &&
                  checkExistsCityAndStateID(destinationCity, destinationState);
        model.addAttribute("success", success);

        if (success) {
            List<CityData> cities = findByProximity(startingCity, startingState);
            for (CityData city : cities) {
        // TEMPORARY, NEED TO FINISH OUT AND INTEGRATE WITH FRONTEND
                System.out.println(city.getCity());
            }
        }

        userData.setDaysOfWeek(daysOfWeek);
        userData.setStartingAddress(startingAddress);
        userData.setDestinationAddress(destinationAddress);

        if (!userData.getStartingAddress().getCity().equals(""))
            model.addAttribute("cityData", userData.getStartingAddress().getCity());

        return "redirect:/add";
    }

    // Check if there exists a city with specified City Name and State ID
    private boolean checkExistsCityAndStateID(String city, String stateID){
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
    public List<CityData> findByProximity(String city, String stateID){
        List<CityData> cityWithSameStateID = this.cityDataRepository.findByStateID(stateID);
        CityData originCity = new CityData();
        for(CityData cityData: cityWithSameStateID){
            if(cityData.getStateID().equals(stateID) && cityData.getCity().equals(city)){
                originCity = cityData;
            }
        }

        //DUMMY RADIUS DELETE LATER DELETE LATER DELETE LATER DELETE LATER DELETE LATER DELETE LATER
        double radius = 30.0;
        //DUMMY RADIUS DELETE LATER DELETE LATER DELETE LATER DELETE LATER DELETE LATER DELETE LATER

        List<CityData> foundCities = new ArrayList<>();
        CityRouteMath cityRouteMath = new CityRouteMath();
        LatLngArea originCityArea = cityRouteMath.getLatLngArea(originCity.getLat(), originCity.getLng(),radius);

        for(int i = 0; i < cityWithSameStateID.size(); i++){
            if(cityWithSameStateID.get(i).getLat() < originCityArea.getLat1() &&
               cityWithSameStateID.get(i).getLat() > originCityArea.getLat3() &&
               cityWithSameStateID.get(i).getLng() < originCityArea.getLng1() &&
               cityWithSameStateID.get(i).getLng() > originCityArea.getLng2())
            {
                foundCities.add(cityWithSameStateID.get(i));
            }
        }

        return foundCities;
    }

}
