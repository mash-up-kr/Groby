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
public class ItemTab1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tabOneId;

    private Date endDate;        // 좋아요 받는 마지막 날
    private String contents;     // 총대의 메시지
    private String location;     // 대략적 위치
}
