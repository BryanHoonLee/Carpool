package com.carpool.jambee.mongodb.controller;

import com.carpool.jambee.CityRouteMath;
import com.carpool.jambee.LatLngArea;
import com.carpool.jambee.mongodb.model.CityData;
import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.mongodb.repository.CityDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    public boolean checkExistsCityAndStateID(String city, String stateID){
        List<CityData> cityList = this.cityDataRepository.findByStateIDAndCity(stateID, city);
        boolean cityAndStateIDExists = false;
        if (cityList.size() > 0){
            cityAndStateIDExists = true;
        }

        return cityAndStateIDExists;
    }

    // Finds all cities in proximity in same state
    public List<CityData> findByProximity(String stateID, String city){
        List<CityData> cityWithSameStateID = this.cityDataRepository.findByStateID(stateID);
        CityData originCity = this.cityDataRepository.findByCityName(stateID, city);

        //DUMMY RADIUS DELETE LATER DELETE LATER DELETE LATER DELETE LATER DELETE LATER DELETE LATER
        double radius = 20.0;
        //DUMMY RADIUS DELETE LATER DELETE LATER DELETE LATER DELETE LATER DELETE LATER DELETE LATER

        List<CityData> foundCities = new ArrayList<>();
        CityRouteMath cityRouteMath = new CityRouteMath();
        LatLngArea originCityArea = cityRouteMath.getLatLngArea(originCity.getLat(), originCity.getLng(),radius);

        for(int i = 0; i < cityWithSameStateID.size(); i++){
            if(cityWithSameStateID.get(i).getLat() > originCityArea.getLat1() &&
                    cityWithSameStateID.get(i).getLat() < originCityArea.getLat2() &&
                    cityWithSameStateID.get(i).getLng() < originCityArea.getLng1() &&
                    cityWithSameStateID.get(i).getLng() > originCityArea.getLng3()){
                foundCities.add(cityWithSameStateID.get(i));
            }
        }

        return foundCities;
    }






}
