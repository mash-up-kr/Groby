package com.example.gonggu.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(exclude = "item")
@Setter
@Getter
@Entity
public class ItemTab5 {
    @Id
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Item item;

    private String contents; // 총대의 메시지
    private String location; // 공구물품 배부장소
    // 공구물품 배부시간
}
