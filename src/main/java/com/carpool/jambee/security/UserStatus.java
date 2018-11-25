package com.carpool.jambee.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserStatus {
    public boolean isUserLoggedIn() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) { // If someone is logged in
            return true;
        } else { // If no one is logged in (also with default name "anonymousUser")
            return false;
        }
    }
}
