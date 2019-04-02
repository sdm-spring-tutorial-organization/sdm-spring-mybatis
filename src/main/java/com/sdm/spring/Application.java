package com.sdm.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication // @EnableAutoConfigration
@RestController
@EnableAspectJAutoProxy
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    Environment env;

    @Value("${spring.profiles.active}")
    private String springProfilesAvtive;

    @Value("${property.hello}")
    private String propertyHello;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * check profile
     */
    @RequestMapping("/profile")
    public String checkProfile() {
        /*
         * [default]
         * env.getActiveProfiles() : []
         * env.getDefaultProfiles() : [default]
         * */
        return "[" + propertyHello + "] " +
                Arrays.toString(env.getActiveProfiles()) + ", " +
                Arrays.toString(env.getDefaultProfiles());
    }

}
