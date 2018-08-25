package com.example.gonggu.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginJson {
    private String userEmail;
    private String userPw;
}
