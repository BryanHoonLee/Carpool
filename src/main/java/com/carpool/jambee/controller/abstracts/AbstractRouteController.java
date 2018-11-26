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
            if(cityWithSameStateID.get(i).getLat() <= originCityArea.getLat1() &&
                    cityWithSameStateID.get(i).getLat() >= originCityArea.getLat3() &&
                    cityWithSameStateID.get(i).getLng() >= originCityArea.getLng1() &&
                    cityWithSameStateID.get(i).getLng() <= originCityArea.getLng2())
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
    protected String interpretDaysOfWeek(boolean[] daysOfWeek) {
        String out = "";

        if (daysOfWeek[0]) out += "S";
        else               out += "-";

        if (daysOfWeek[1]) out += "M";
        else               out += "-";

        if (daysOfWeek[2]) out += "T";
        else               out += "-";

        if (daysOfWeek[3]) out += "W";
        else               out += "-";

        if (daysOfWeek[4]) out += "T";
        else               out += "-";

        if (daysOfWeek[5]) out += "F";
        else               out += "-";

        if (daysOfWeek[6]) out += "S";
        else               out += "-";

        if (out.equals("-------"))
            out = "No specified days";

        return out;
    }

    protected boolean checkIfInputIsValid( ArrayList<String> messages,
                                         String startingCity, String startingState, int startingCityRadius,
                                         String destinationCity, String destinationState, int destinationCityRadius
    ) {
        boolean result = true;

        if (!checkExistsCityAndStateID(startingCity, startingState)) {
            result = false;
            messages.add("Starting city doesn't exist.");
        }
        if (!checkExistsCityAndStateID(destinationCity, destinationState)) {
            result = false;
            messages.add("Destination city doesn't exist.");
        }
        if (startingCityRadius > 50) {
            result = false;
            messages.add("Starting city radius cannot be greater than 50 miles");
        }
        if (startingCityRadius < 1) {
            result = false;
            messages.add("Starting city radius cannot be less than 1 mile");
        }
        if (destinationCityRadius > 50) {
            result = false;
            messages.add("Starting city radius cannot be greater than 50 miles");
        }
        if (destinationCityRadius < 1) {
            result = false;
            messages.add("Destination city radius cannot be less than 1 mile");
        }
        return result;
    }

}
