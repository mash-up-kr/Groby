package com.example.gonggu.domain.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uno;
    private String name;
    private Date created;

    public User(String name){
        this.name = name;
        this.created = new Date();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("User name -> "+name+'\n');
        sb.append("created Date -> " + created+'\n');
        return sb.toString();
    }

}
