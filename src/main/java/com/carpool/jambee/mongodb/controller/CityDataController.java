package com.carpool.jambee.mongodb.controller;

import com.carpool.jambee.mongodb.model.CityData;
import com.carpool.jambee.mongodb.repository.CityDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CityDataController {

    @Autowired
    private CityDataRepository cityDataRepository;

    // Get all cities in United States
    @GetMapping("/data/city/all")
    public List<CityData> getAllCityData() {
        List<CityData> cityData = this.cityDataRepository.findAll();
        return cityData;
    }

    @GetMapping("/data/city/names")
    public List<String> getCityNames() {
        List<CityData> cityData = this.cityDataRepository.findAll();
        List<String> cityNames = new ArrayList<>();

        for (CityData city : cityData) {
            cityNames.add(city.getCity());
        }

        return cityNames;
    }

    // Get all cities in same STATE
    @GetMapping("/data/city/all/{stateID}")
    public List<CityData> getCitiesDataByStateID(@PathVariable("stateID") String stateID){
        List<CityData> cityWithSameStateID = this.cityDataRepository.findByStateID(stateID);

        return cityWithSameStateID;
    }

    @GetMapping("/data/city/names/{stateID}")
    public List<String> getCityNamesByStateID(@PathVariable("stateID") String stateID){
        List<CityData> cityWithSameStateID = this.cityDataRepository.findByStateID(stateID);

        List<String> cityNames = new ArrayList<>();

        for (CityData city : cityWithSameStateID)
            cityNames.add(city.getCity());

        return cityNames;
    }

    @GetMapping("/data/city/states/names")
    public List<String> getStateNames() {
        List<CityData> cityData = this.cityDataRepository.findAll();
        List<String> stateNames = new ArrayList<>();

        for (CityData city : cityData) {
            if (!stateNames.contains(city.getStateName()))
                stateNames.add(city.getStateName());
        }

        return stateNames;
    }

}
