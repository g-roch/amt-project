package com.amt.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PianorgueApp extends SpringBootServletInitializer {

    // To start Spring boot (the app)
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        SpringApplication.run(PianorgueApp.class, args);
    }




}
