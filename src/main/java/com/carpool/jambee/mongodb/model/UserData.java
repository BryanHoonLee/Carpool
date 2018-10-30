package com.carpool.jambee.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "userData")
public class UserData {
    @Id
    private String id;

}
