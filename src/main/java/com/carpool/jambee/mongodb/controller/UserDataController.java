package com.carpool.jambee.mongodb.controller;

import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.mongodb.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserDataController {

    @Autowired
    private UserDataRepository userDataRepository;

    // Get all User Data
    @GetMapping("/profile/data")
    public List<UserData> getAllProfileData(){
        List<UserData> profileData = this.userDataRepository.findAll();
        return profileData;
    }

    // Create User information
    @PutMapping("/profile/data")
    public void insertProfileData(@RequestBody UserData userData){
        this.userDataRepository.insert(userData);
    }

    // Update User information
    @PostMapping("/profile/data")
    public void updateProfileData(@RequestBody UserData userData){
        this.userDataRepository.save(userData);
    }

    // Delete User information by Id
    @DeleteMapping("/profile/data/{id}")
    public void delete(@PathVariable("id") String id){
        this.userDataRepository.deleteById(id);
    }


    // Pulls all users with SAME STARTING CITY AND STATE
    @GetMapping("/start/address/{city}/{state}")
    public List<UserData> getSameStartAddress(@PathVariable("city") String city, @PathVariable("state") String state){
        List<UserData> userWithSameStartAddress = this.userDataRepository.findByStartingAddress(city, state);

        return userWithSameStartAddress;
    }

    // Pulls all users with SAME DESTINATION CITY AND STATE
    @GetMapping("/destination/address/{city}/{state}")
    public List<UserData> getSameDestinationtAddress(@PathVariable("city") String city, @PathVariable("state") String state){
        List<UserData> userWithSameDestinationAddress = this.userDataRepository.findByDestinationAddress(city, state);

        return userWithSameDestinationAddress;
    }

    // Pulls all users with SAME STARTING CITY
    @GetMapping("/start/address/city/{city}")
    public List<UserData> getSameStartingCity(@PathVariable("city") String city){
        List<UserData> userWithSameStartingCity = this.userDataRepository.findByStartingCity(city);

        return userWithSameStartingCity;
    }

    // Pulls all users with SAME DESTINATION CITY
    @GetMapping("/destination/address/city/{city}")
    public List<UserData> getSameDestinationCity(@PathVariable("city") String city){
        List<UserData> userWithSameDestinationCity = this.userDataRepository.findByDestinationCity(city);

        return userWithSameDestinationCity;
    }

    // Pulls all users with SAME STARTING STATE
    @GetMapping("/start/address/state/{state}")
    public List<UserData> getSameStartingState(@PathVariable("state") String state){
        List<UserData> userWithSameStartingState = this.userDataRepository.findByStartingState(state);

        return userWithSameStartingState;
    }

    // Pulls users with SAME DESTINATION STATE
    @GetMapping("/destination/address/state/{state}")
    public List<UserData> getSameDestinationState(@PathVariable("state") String state){
        List<UserData> userWithSameDestinationState = this.userDataRepository.findByDestinationState(state);

        return userWithSameDestinationState;
    }


}
