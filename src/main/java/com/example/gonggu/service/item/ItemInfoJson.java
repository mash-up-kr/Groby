package com.example.gonggu.service.item;

import com.example.gonggu.domain.item.ItemImgPath;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ItemInfoJson {
    private Long itemId;
    private Long writerId;
    private String itemTitle;
    private String category;
    private Integer nowTab;
    private Integer numOfLike;
    private Integer partcipantPercent; // Tab2일 경우 공구 참여 퍼센트

    // tab1에 대한 정보
    private String oneEndDate;
    private String oneContents;
    private String oneLocation;

    // tab2에 대한 정보
    private String twoContents;
    private String twoEndDate;
    private String optionString;

    // tab3
    private String threeContents;

    // tab4
    private String fourContents;
    private String fourArrivedTime;

    // tab5
    private String fiveContents;
    private String fiveLocationDetail;

    private Boolean isDeleted;
    private List<ItemImgPath> imgList;
}
