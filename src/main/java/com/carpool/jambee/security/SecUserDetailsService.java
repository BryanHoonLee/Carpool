package com.carpool.jambee.security;

import com.carpool.jambee.mongodb.model.UserData;
import com.carpool.jambee.mongodb.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SecUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserData user = userDataRepository.findBySignupEmailEquals(email);

        class TEMP implements GrantedAuthority {

            @Override
            public String getAuthority() {
                return "";
            }
        }

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        else {
            return new User(user.getSignupEmail(),user.getPassword(), new ArrayList<TEMP>());
        }
    }
}
