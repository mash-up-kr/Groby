package com.example.gonggu.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class ItemTab5 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tabFiveId;

    private String contents;                // 총대의 메시지
    private String locationDetail;          // 공구물품 배부장소
    private String distributionTime;        // 공구물품 배부시간 (ex. 2018-01-02-1234/2018-01-02-1534)
}
