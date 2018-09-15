package com.example.gonggu.dto.item;

import com.example.gonggu.domain.item.ItemImgPath;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ItemPatchJson {
    private String itemId;
    private String writerId;
    private Boolean editTab;
    private String category;
    private String itemTitle;
    private Integer targetTab;

    private String[] imgPathList;
    private Integer itemAmountLimit;

    private ItemTab1Json tab1;
    private ItemTab2Json tab2;
    private ItemTab3Json tab3;
    private ItemTab4Json tab4;
    private ItemTab5Json tab5;
}
