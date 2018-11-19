package com.carpool.jambee.mongodb.controller;

import com.carpool.jambee.CityRouteMath;
import com.carpool.jambee.LatLngArea;
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

    @PostMapping("/check/city/name")
    public boolean isCityReal(String city) {
        Optional<CityData> op = this.cityDataRepository.findById(city);

        return op.isPresent();
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

    // Check if there exists a city with specified City Name and State ID
    public boolean checkExistsCityAndStateID(String city, String stateID){
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
    public List<CityData> findByProximity(String stateID, String city){
        List<CityData> cityWithSameStateID = this.cityDataRepository.findByStateID(stateID);
        CityData originCity = new CityData();
        for(CityData cityData: cityWithSameStateID){
            if(cityData.getStateID().equals(stateID) && cityData.getCity().equals(city)){
                originCity = cityData;
            }
        }

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
