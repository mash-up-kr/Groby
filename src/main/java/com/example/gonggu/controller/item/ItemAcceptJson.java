package com.example.gonggu.controller.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
public class ItemAcceptJson {
    private String a_TabNumber;

    private Optional<String> itemUserEmail;
    private Optional<String> itemCategory;
    private Optional<String> itemTitle;
    // tab one
    private Optional<String> oneImgPath;      // 이미지 주소
    private Optional<String> oneEndDate;        // 좋아요 받는 마지막 날
    private Optional<String> oneContents;     // 총대의 메시지
    private Optional<String> oneLocation;     // 대략적 위치
    //tab two
    private Optional<String> twoImgPath;          // 이미지 주소
    private Optional<String> twoContents;         // 총대의 메시지
    private Optional<String> twoEndDate;            // 공구 종료시점
    private Optional<String> twoOptionString;           // 옵션값 한줄로 저장
    //tab four
    private Optional<String> fourContents;       // 총대의 메시지
    private Optional<String> fourReceiptImgPath; // 영수증 사진 주소
    private Optional<String> fourArrivedTime;      // 도착예정시간
    //tab five
    private Optional<String> fiveContents;                // 총대의 메시지
    private Optional<String> fiveLocationDetail;          // 공구물품 배부장소
    private Optional<String> fiveDistributionTime;        // 공구물품 배부시간 (ex. 2018-01-02-1234/2018-01-02-1534)
}
