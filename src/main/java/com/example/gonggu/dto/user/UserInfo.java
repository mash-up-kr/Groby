package com.example.gonggu.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String userId;
    private String userEmail;
    private String userName;
    private String accountNum;
    private String accountBank;
    private String accountHolder;
    private Boolean isDeleted;

    private Boolean denied;
    private String phoneNumber;

}
