package com.carpool.jambee.controller.abstracts;

import com.carpool.jambee.CityRouteMath;
import com.carpool.jambee.LatLngArea;
import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.CityData;
import com.carpool.jambee.mongodb.repository.CityDataRepository;
import com.carpool.jambee.mongodb.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRouteController {
    @Autowired
    protected UserDataRepository userDataRepository;
    @Autowired
    protected CityDataRepository cityDataRepository;

    // Finds all cities in proximity in same state
    protected List<Address> findByProximity(String city, String stateID, double radius) {
        if (radius <= 0) radius = 1;

        List<CityData> cityWithSameStateID = this.cityDataRepository.findByStateID(stateID);
        CityData originCity = new CityData();
        for (CityData cityData : cityWithSameStateID) {
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

    protected int milesToKM(int miles) {
        return (int)((double)miles * 1.609344);
    }

    protected int kmToMiles(int km) {
        return (int)((double)km * 0.6213712);
    }

    // Check if there exists a city with specified City Name and State ID
    protected boolean checkExistsCityAndStateID(String city, String stateID) {
        List<CityData> cityList = this.cityDataRepository.findByStateIDAndCity(stateID, city);
        boolean cityAndStateIDExists = false;
        for(int i = 0; i < cityList.size(); i++){
            if (cityList.get(i).getCity().equals(city) && cityList.get(i).getStateID().equals(stateID)){
                cityAndStateIDExists = true;
            }
        }
        return cityAndStateIDExists;
    }
}
