package com.example.gonggu.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserSignupJson {
    @NotNull
    private String userEmail;
    @NotNull
    private String userPw;
    @NotNull
    private String userName;
    private String userToken;
}
