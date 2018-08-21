package com.example.gonggu.controller.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinAcceptJson {
    private String itemId;
    private String userName;
    private String accountNum;
    private String accountBank;
    private String accountHolder;
    private String optionString;
    private Integer amount;
    private Integer price;

}
