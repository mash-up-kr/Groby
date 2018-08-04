package com.example.gonggu.domain.user;


import javax.persistence.Entity;
import javax.persistence.Id;

// Dummy data


public class User {

    public Long id;
    public String name;



    public User(String name){
        this.name = name;
        this.id = 123L;
    }

    public User(long usernum){
        this.id = usernum;
        this.name = "onemoon";
    }

}
