package com.example.gonggu.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserPatchJson {
    @NotNull
    private String userEmail;
    private String userName;
    private String accountNum;
    private String accountBank;
    private String accountHolder;
    private String phoneNumber;
}
