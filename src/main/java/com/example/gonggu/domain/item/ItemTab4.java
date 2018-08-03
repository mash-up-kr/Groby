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
public class ItemTab4 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tab4Id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String contents;       // 총대의 메시지
    private String receiptImgPath; // 영수증 사진 주소
    private Date arrivedTime;      // 도착예정시간

}
