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

    @Column(length = 2000)
    private String contents;                // 총대의 메시지
    @Column(length = 1000)
    private String locationDetail;          // 공구물품 배부장소
}
