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
public class ItemTab4 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tabFourId;

    private String contents;       // 총대의 메시지
    private Date arrivedTime;      // 도착예정시간
}
