package com.carpool.jambee.mongodb.seeder;

import com.carpool.jambee.mongodb.model.CityData;
import com.carpool.jambee.CsvReader;
import com.carpool.jambee.mongodb.repository.CityDataRepository;
import com.carpool.jambee.mongodb.repository.UserDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {
    private UserDataRepository userDataRepository;
    private CityDataRepository cityDataRepository;

    public DbSeeder(UserDataRepository userDataRepository, CityDataRepository cityDataRepository){
        this.userDataRepository = userDataRepository;
        this.cityDataRepository = cityDataRepository;
    }

    // When spring boot app starts, the command line runner will be executed automatically by spring
    @Override
    public void run(String... args) throws Exception {

        CsvReader csvReader = new CsvReader();
        List<CityData> cityDataList = csvReader.readFile("uscities.csv");
        // D U M M Y DATA
//        UserData userData = new UserData(
//                new Address("Temecula", "CA"),
//                new Address("Pomona", "CA"),
//                new boolean[7],
//                "bryanlee@cpp.edu",
//                "123-456-7897",
//                "No drinks or food allowed",
//                "Food"
//        );
//
//        UserData userData2 = new UserData(
//                new Address("Walnut", "CA"),
//                new Address("Pomona", "CA"),
//                new boolean[7],
//                "james@cpp.edu",
//                "999-999-9999",
//                "No Peanuts",
//                "Food"
//        );

//        List<UserData> topics = Arrays.asList(userData, userData2);
//        this.userDataRepository.saveAll(topics);

//        this.userDataRepository.deleteAll();

//        this.cityDataRepository.deleteAll();
//        this.cityDataRepository.saveAll(cityDataList);

    }

}
