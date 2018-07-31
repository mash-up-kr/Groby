package com.example.gonggu.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString(exclude = "item")
@Setter
@Getter
@Entity
public class ItemTab2 {
    @Id
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Item item;

    private Date regDate; // 글 등록 시점
    private String imgPath; // 이미지 주소
    private Integer amount; // 참여자의 수 (?)
    private String contents; // 총대의 메시지
    private Date endDate2; // 공구 종료시점

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tab2Id; // 이게 primary가 되어야하는 게 아닌가..?
}