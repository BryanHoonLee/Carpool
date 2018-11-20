package com.carpool.jambee.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "userData")
public class UserData {
    @Id
    private String id;
    private Address startingAddress;
    private Address destinationAddress;
    private boolean daysOfWeek[];
    private String email;
    private String phoneNumber;
    private String additionalNotes;
    private String preferredCompensation;

    public UserData() {
    }

    public UserData(Address startingAddress, Address destinationAddress, boolean[] daysOfWeek, String email, String phoneNumber,
                    String additionalNotes, String preferredCompensation) {
        this.startingAddress = startingAddress;
        this.destinationAddress = destinationAddress;
        this.daysOfWeek = daysOfWeek;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.additionalNotes = additionalNotes;
        this.preferredCompensation = preferredCompensation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(Address startingAddress) {
        this.startingAddress = startingAddress;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public boolean[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(boolean[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getPreferredCompensation() {
        return preferredCompensation;
    }

    public void setPreferredCompensation(String preferredCompensation) {
        this.preferredCompensation = preferredCompensation;
    }
}
