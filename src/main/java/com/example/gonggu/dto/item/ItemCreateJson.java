package com.example.gonggu.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemCreateJson {
    private String itemCategoryId;
    private String itemTitle;
    private String itemAmountLimit;
//    private String userEmail;
    private String userId;
    private String[] imgPathList;

    private ItemTab1Json tab1;
}
