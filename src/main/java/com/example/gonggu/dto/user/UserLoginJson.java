package com.example.gonggu.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserLoginJson {
    @NotNull
    private String userEmail;
    @NotNull
    private String userPw;
}
