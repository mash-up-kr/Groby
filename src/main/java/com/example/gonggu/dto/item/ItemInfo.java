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
    private String itemId;
    private String writerId;
    private String writerUserName;
    private String category;
    private String itemTitle;
    private Integer nowTab;
    private Integer numOfLike;
    private Integer numOfParticipant;
    private Integer participantPercent; //Tab2일 경우 공구 참여 퍼센트

    private List<ItemImgPath> imgPathList;
    private Boolean isDeleted;

    private ItemTab1Json tabOne;
    private ItemTab2Json tabTwo;
    private ItemTab3Json tabThree;
    private ItemTab4Json tabFour;
    private ItemTab5Json tabFive;
}