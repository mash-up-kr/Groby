package com.example.gonggu.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSignupJson {
    private String userEmail;
    private String userPw;
    private String userName;
    private String userToken;
}
