package com.carpool.jambee.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NewUser {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String signupEmail;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @Autowired
    BCryptPasswordEncoder encoder;

    public NewUser(String firstName, String lastName, String signupEmail, String password, String matchingPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.signupEmail= signupEmail;
        this.password = password;
        this.matchingPassword = matchingPassword;

        encoder = new BCryptPasswordEncoder();
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
        this.password = encoder.encode(password);
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public boolean doPasswordsMatch() {
        return encoder.matches(matchingPassword, password);
    }

}
