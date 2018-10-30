package com.carpool.jambee;

import com.carpool.jambee.model.NewUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarpoolService {

    public static void main (String[] args) {
        SpringApplication.run(CarpoolService.class, args);
    }
}
