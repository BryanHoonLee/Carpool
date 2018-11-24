package com.carpool.jambee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarpoolService {

    public static void main (String[] args) {
        // City data provided by "https://simplemaps.com/data/us-cities"
        SpringApplication.run(CarpoolService.class, args);
    }
}
