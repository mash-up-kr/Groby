package com.example.gonggu.dto.item;

import com.example.gonggu.domain.item.ItemImgPath;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ItemInfo {
    private Long itemId;
    private Long writerId;
    private String category;
    private String itemTitle;
    private Integer nowTab;
    private Integer numOfLike;
    private Integer numOfParticipant;
    private Integer participantPercent; //Tab2일 경우 공구 참여 퍼센트

    private List<ItemImgPath> imgPathList;
    private Boolean isDeleted;

    private ItemTab1Json tab1;
    private ItemTab2Json tab2;
    private ItemTab3Json tab3;
    private ItemTab4Json tab4;
    private ItemTab5Json tab5;
}