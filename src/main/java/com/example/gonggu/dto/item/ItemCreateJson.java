package com.example.gonggu.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ItemCreateJson {
    @NotNull
    private String itemCategoryId;
    @NotNull
    private String itemTitle;
    @NotNull
    private String itemAmountLimit;
//    private String userEmail;
    @NotNull
    private String userId;
    @NotNull
    private String[] imgPathList;
    @NotNull
    private ItemTab1Json tabOne;
}
