package com.itsfolarin.starter.springflowbase;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@EnableVaadin
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FlowSpringApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FlowSpringApplication.class, args);
    }

}
