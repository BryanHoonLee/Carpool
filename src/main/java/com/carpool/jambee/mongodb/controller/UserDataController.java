package com.carpool.jambee.mongodb.controller;

import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.mongodb.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserDataController {

    @Autowired
    private UserDataRepository userDataRepository;

    @GetMapping("/profile/data")
    public List<UserData> getAllProfileData(){
        List<UserData> profileData = this.userDataRepository.findAll();
        return profileData;
    }

    @PutMapping("/profile/data")
    public void insertProfileData(@RequestBody UserData userData){
        this.userDataRepository.insert(userData);
    }

    @PostMapping("/profile/data")
    public void updateProfileData(@RequestBody UserData userData){
        this.userDataRepository.save(userData);
    }

    @DeleteMapping("/profile/data/{id}")
    public void delete(@PathVariable("id") String id){
        this.userDataRepository.deleteById(id);
    }


}
