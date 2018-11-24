package com.carpool.jambee.mongodb.model;

public class Address {
    private String city;
    private String stateID;

    public Address(){

    }

    public Address(String city, String stateID){
        this.city = city;
        this.stateID = stateID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }
}
