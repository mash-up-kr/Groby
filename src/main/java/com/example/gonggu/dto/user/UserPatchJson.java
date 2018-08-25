package com.example.gonggu.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPatchJson {
    private String userEmail;
    private String userName;
    private String accountNum;
    private String accountBank;
    private String accountHolder;
}
