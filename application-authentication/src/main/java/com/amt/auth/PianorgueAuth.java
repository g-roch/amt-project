package com.amt.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PianorgueAuth extends SpringBootServletInitializer {

    // To start Spring boot (the app)
    public static void main(String[] args) {
        SpringApplication.run(PianorgueAuth.class, args);
    }


}
