package com.amt.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PianorgueApp extends SpringBootServletInitializer {

    // To start Spring boot (the app)
    public static void main(String[] args) {
        SpringApplication.run(PianorgueApp.class, args);
    }




}
