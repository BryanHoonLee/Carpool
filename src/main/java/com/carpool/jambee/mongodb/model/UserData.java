package com.carpool.jambee.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "userData")
public class UserData {
    @Id
    private String id;
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String password;
    private Address startingAddress;
    private Address destinationAddress;
    private boolean daysOfWeek[];
    private String signupEmail;
    private String email;
    private String phoneNumber;
    private String additionalNotes;
    private String preferredCompensation;

    public UserData() {
    }

    public UserData(String firstName, String lastName, String signupEmail, String password,
                    Address startingAddress, Address destinationAddress,
                    boolean[] daysOfWeek,
                    String email, String phoneNumber,
                    String additionalNotes, String preferredCompensation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.signupEmail = signupEmail;
        this.password = password;
        this.startingAddress = startingAddress;
        this.destinationAddress = destinationAddress;
        this.daysOfWeek = daysOfWeek;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.additionalNotes = additionalNotes;
        this.preferredCompensation = preferredCompensation;

        this.enabled = true;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserData)){
            return false;
        }

        UserData userData = (UserData) obj;

        return id.equals(userData.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSignupEmail() {
        return signupEmail;
    }

    public void setSignupEmail(String signupEmail) {
        this.signupEmail = signupEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
