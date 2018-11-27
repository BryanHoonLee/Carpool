package com.carpool.jambee.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class MultiSecurityConfig {

    @Configuration
    @Order(1)
    public static class SessionSecurityConfig extends WebSecurityConfigurerAdapter {
        //@Autowired
        //PasswordEncoder passwordEncoder;

        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                    .antMatchers("/", "/index", "/home").permitAll()
                    .antMatchers("/register").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .failureUrl("/login?error").permitAll()
                    .defaultSuccessUrl("/", true)
                    .and()
                .logout()
                    .logoutUrl("/logout").permitAll()
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true);
        }
    }

    @Configuration
    @Order(Integer.MAX_VALUE - 1)
    public static class GeneralSecurityConfig extends WebSecurityConfigurerAdapter {
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

    @Configuration
    @EnableConfigurationProperties
    public static class AuthSecurityConfig extends GlobalAuthenticationConfigurerAdapter {
        @Autowired
        SecUserDetailsService secUserDetailsService;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(secUserDetailsService);
            //builder.authenticationProvider(authProvider());
            //builder.inMemoryAuthentication().withUser("testUser").password("superpassword");
            //builder.authenticationProvider(authProvider());
        }

        @Bean
        public static PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        public DaoAuthenticationProvider authProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(secUserDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }
    }

}
