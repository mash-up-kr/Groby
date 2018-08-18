package com.example.gonggu.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class PropertiesConfig {

    @Autowired
    private Environment environment;

    @PostConstruct
    public void postCon(){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("Check Profile");
        System.out.println(Arrays.toString(getActiveProfiles()));
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    public String[] getActiveProfiles() { return environment.getActiveProfiles(); }

}
