package com.carpool.jambee.mongodb.controller;

import com.carpool.jambee.CityData;
import com.carpool.jambee.mongodb.repository.CityDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityDataController {

    @Autowired
    private CityDataRepository cityDataRepository;

    @GetMapping("/city/data")
    public List<CityData> getAllCityData() {
        List<CityData> cityData = this.cityDataRepository.findAll();
        return cityData;
    }
}
