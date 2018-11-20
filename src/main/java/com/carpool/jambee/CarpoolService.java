package com.carpool.jambee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Random;

@SpringBootApplication
public class CarpoolService {

    public static void main (String[] args) {
        //CsvReader citiesCSV = new CsvReader();

        // City data provided by "https://simplemaps.com/data/us-cities"
        //   We are REQUIRED to put that URL somewhere on our website
       // ArrayList<CityData> citiesData = citiesCSV.readFile("classpath:uscities.csv");

        // At this point, we need to store the city data into the database

        SpringApplication.run(CarpoolService.class, args);
    }
}
