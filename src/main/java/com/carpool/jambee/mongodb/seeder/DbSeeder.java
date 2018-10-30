package com.carpool.jambee.mongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {
    private UserDataRepository userDataRepository;

    public DbSeeder(UserDataRepository userDataRepository){
        this.userDataRepository = userDataRepository;
    }

    // When spring boot app starts, the command line runner will be executed automatically by spring
    @Override
    public void run(String... args) throws Exception {

    }

}
