package com.example.gonggu.service.item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PopularItemList {
    private Long itemId;
    private String title;
    private String imgPath;
    private Integer likeNum;        // 좋아요 수
    private Integer participantNum; // 참여한 사람의 수
    private Integer amountLimit;    // 공구 수량
    private Integer participantPercent; // 진행률
}
