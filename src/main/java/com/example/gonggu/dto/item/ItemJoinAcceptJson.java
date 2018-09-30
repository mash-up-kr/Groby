package com.example.gonggu.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ItemJoinAcceptJson {
    @NotNull
    private String itemId;
    @NotNull
    private String userName;
    @NotNull
    private String accountNum;
    @NotNull
    private String accountBank;
    @NotNull
    private String accountHolder;
    @NotNull
    private String optionString;
    @NotNull
    private Integer amount;
    @NotNull
    private Integer price;

}
