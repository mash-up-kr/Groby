package com.example.gonggu.dto.item;

import com.example.gonggu.domain.item.ItemImgPath;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class ItemPatchJson {
    @NotNull
    private String itemId;
    @NotNull
    private String writerId;
    @NotNull
    private Boolean editTab;
    @NotNull
    private String category;
    @NotNull
    private String itemTitle;
    @NotNull
    private Integer targetTab;

    @NotNull
    private String[] imgPathList;
    @NotNull
    private Integer itemAmountLimit;

    private ItemTab1Json tabOne;
    private ItemTab2Json tabTwo;
    private ItemTab3Json tabThree;
    private ItemTab4Json tabFour;
    private ItemTab5Json tabFive;
}
