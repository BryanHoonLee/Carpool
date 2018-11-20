package com.carpool.jambee.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableWebSecurity
public class MultiSecurityConfig {

    @Configuration
    @Order(1)
    public static class SessionSecurityConfig extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                    .antMatchers("/", "/index", "/home", "/index.html").permitAll()
                    .antMatchers("/register", "/register/complete").permitAll()
                    .antMatchers("/add", "/add/submit").permitAll()
                    .antMatchers("/search").permitAll()
                    .antMatchers("/test").hasRole("USER")
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
    public static class TemporaryUsersSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user").password("password").roles("USER")
                    .and()
                    .withUser("admin").password("password").roles("ADMIN")
                    .and()
                    .withUser("anonymousUser").password("password").roles("ADMIN");
        }

        @SuppressWarnings("deprecation")
        @Bean
        public static NoOpPasswordEncoder passwordEncoder() {
            return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
        }
    }

}
