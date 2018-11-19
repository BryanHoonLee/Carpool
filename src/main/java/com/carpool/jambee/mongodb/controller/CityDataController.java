package com.carpool.jambee.mongodb.controller;

import com.carpool.jambee.mongodb.model.CityData;
import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.mongodb.repository.CityDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityDataController {

    @Autowired
    private CityDataRepository cityDataRepository;

    // Get all cities in United States
    @GetMapping("/city/data")
    public List<CityData> getAllCityData() {
        List<CityData> cityData = this.cityDataRepository.findAll();
        return cityData;
    }

    // Get all cities in same STATE
    @GetMapping("/city/{stateID}")
    public List<CityData> getCityByStateID(@PathVariable("stateID") String stateID){
        List<CityData> cityWithSameStateID = this.cityDataRepository.findByStateID(stateID);

        return cityWithSameStateID;
    }

    // Check if there exists a city with specified City Name and State ID
    public boolean checkExistsCityAndStateID(@PathVariable("city") String city, @PathVariable("stateID") String stateID){
        List<CityData> cityList = this.cityDataRepository.findByStateIDAndCity(stateID, city);
        boolean cityAndStateIDExists = false;
        if (cityList.size() > 0){
            cityAndStateIDExists = true;
        }

        return cityAndStateIDExists;
    }


}
