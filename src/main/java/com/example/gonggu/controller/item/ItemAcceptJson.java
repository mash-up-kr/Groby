package com.example.gonggu.controller.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;
import java.util.OptionalLong;

@Getter
@Setter
@ToString
public class ItemAcceptJson {
    private String a_TabNumber;
    private String a_itemId;
    private String a_userId;
    private String userLikeEmail;

    private String itemUserEmail;
    private String itemCategory;
    private String itemTitle;
    // tab one
    private String oneImgPath;      // 이미지 주소
    private String oneEndDate;        // 좋아요 받는 마지막 날
    private String oneContents;     // 총대의 메시지
    private String oneLocation;     // 대략적 위치
    //tab two
    private String twoImgPath;          // 이미지 주소
    private String twoContents;         // 총대의 메시지
    private String twoEndDate;            // 공구 종료시점
    private String twoOptionString;           // 옵션값 한줄로 저장
    //tab four
    private String fourContents;       // 총대의 메시지
    private String fourReceiptImgPath; // 영수증 사진 주소
    private String fourArrivedTime;      // 도착예정시간
    //tab five
    private String fiveContents;                // 총대의 메시지
    private String fiveLocationDetail;          // 공구물품 배부장소
    private String fiveDistributionTime;        // 공구물품 배부시간 (ex. 2018-01-02-1234/2018-01-02-1534)

//    public void setA_itemId(Long check) {
//        this.a_itemId = new Optional<Long>();
//    }
}
