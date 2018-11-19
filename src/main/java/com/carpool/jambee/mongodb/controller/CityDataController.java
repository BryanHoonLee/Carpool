package com.carpool.jambee.mongodb.controller;

import com.carpool.jambee.CityData;
import com.carpool.jambee.mongodb.repository.CityDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CityDataController {

    @Autowired
    private CityDataRepository cityDataRepository;

    @GetMapping("/data/city/all")
    public List<CityData> getAllCityData() {
        List<CityData> cityData = this.cityDataRepository.findAll();
        return cityData;
    }

    @GetMapping("/data/city/names")
    public ArrayList<String> getCityNames() {
        List<CityData> cityData = this.cityDataRepository.findAll();
        ArrayList<String> cityNames = new ArrayList<>();

        for (CityData city : cityData) {
            cityNames.add(city.getCity());
        }

        return cityNames;
    }

    @PostMapping("/data/city/names")
    public ArrayList<String> getCityNames(String stateName) {
        ArrayList<String> TEMP = new ArrayList<>();
        TEMP.add("firstData");
        TEMP.add("yep");
        TEMP.add("sure thing");

        return TEMP;
    }

    @PostMapping("/check/city/name")
    public boolean isCityReal(String city) {
        Optional<CityData> op = this.cityDataRepository.findById(city);

        return op.isPresent();
    }

    @GetMapping("/data/city/states/names")
    public ArrayList<String> getStateNames() {
        List<CityData> cityData = this.cityDataRepository.findAll();
        ArrayList<String> stateNames = new ArrayList<>();

        for (CityData city : cityData) {
            if (!stateNames.contains(city.getStateName()))
                stateNames.add(city.getStateName());
        }

        return stateNames;
    }
}
