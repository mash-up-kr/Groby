package com.example.gonguback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@ToString

public class User {

    private int user_num;
    private String user_id;
    private String user_pw;
    private String user_name;
    private String user_email;
    private String account_num;
    private String account_bank;
    private String account_holder;


}
