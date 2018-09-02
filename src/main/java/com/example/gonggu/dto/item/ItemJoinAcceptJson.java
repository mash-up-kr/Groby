package com.example.gonggu.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemJoinAcceptJson {
    private String userName;
    private String accountNum;
    private String accountBank;
    private String accountHolder;
    private String optionString;
    private Integer amount;
    private Integer price;

}
