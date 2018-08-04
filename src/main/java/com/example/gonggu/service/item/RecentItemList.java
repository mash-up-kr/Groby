package com.example.gonggu.service.item;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

// 메인화면에서 최신글을 보여주기에 필요한 정보를 담은 객체
@Getter
@Setter
public class RecentItemList {
    private Long itemId;
    private String title;
    private Integer currentTap;     // 현재 몇번째 탭에 있는지
    private Integer likeNum;        // 탭1일 경우, 좋아요 수
    private Integer participantNum; // 탭2일 경우, 참여한 사람의 수
    private Integer amountLimit;    // 탭2일 경우, 공구 수량
    private String imgPath;         // 이미지 주소
    private Date dueDate;           // 마감 날짜
}
