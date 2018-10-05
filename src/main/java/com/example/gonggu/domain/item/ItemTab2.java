package com.example.gonggu.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class ItemTab2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tabTwoId;
    @Column(length = 2000)
    private String contents;            // 총대의 메시지
    @Column(length = 1000)
    private String optionString;        // 옵션값 한줄로 저장
    private int price;                  // 가격
    private Date endDate;               // 공구 종료시점
}