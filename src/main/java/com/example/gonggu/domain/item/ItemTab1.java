package com.example.gonggu.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(exclude = "item")
@Data
public class ItemTab1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tab1Id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @CreationTimestamp
    private Timestamp regDate;   // item에도 regDate가 있는데 여기에 있는 이유를 잘 모르겠음...
    @UpdateTimestamp
    private Timestamp updateDate;
    private String imgPath;      // 이미지 주소
    private Integer amountLimit; // 공구 오픈 최소 수량
    private Date endDate1;       // 좋아요 받는 마지막 날
    private String contents;     // 총대의 메시지
}
