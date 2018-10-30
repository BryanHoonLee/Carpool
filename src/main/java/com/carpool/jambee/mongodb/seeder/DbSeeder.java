package com.carpool.jambee.mongodb.seeder;

import com.carpool.jambee.mongodb.model.Address;
import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.mongodb.repository.UserDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;

@Component
public class DbSeeder implements CommandLineRunner {
    private UserDataRepository userDataRepository;

    public DbSeeder(UserDataRepository userDataRepository){
        this.userDataRepository = userDataRepository;

    }

    // When spring boot app starts, the command line runner will be executed automatically by spring
    @Override
    public void run(String... args) throws Exception {

        // D U M M Y DATA
        UserData userData = new UserData(
                new Address("Temecula", "CA"),
                new Address("Pomona", "CA"),
                new boolean[7],
                "bryanlee@cpp.edu",
                "123-456-7897",
                "No drinks or food allowed",
                "Food"
        );

        UserData userData2 = new UserData(
                new Address("Walnut", "CA"),
                new Address("Pomona", "CA"),
                new boolean[7],
                "james@cpp.edu",
                "999-999-9999",
                "No Peanuts",
                "Food"
        );
    }

}
