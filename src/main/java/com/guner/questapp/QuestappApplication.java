package com.guner.questapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
*
 * Social Media Media Quest App
 * Backend Spring Boot + Frontend React
 * @author guner.kalan 
*
*/
@SpringBootApplication
public class QuestappApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(QuestappApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(QuestappApplication.class);
    }

}
