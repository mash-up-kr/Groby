package com.example.gonggu.domain.item;

import com.example.gonggu.domain.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(exclude = {"user", "itemTab1", "itemTab2", "itemTab4", "itemTab5"})
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;

    private String category;    // 카테고리
    private String title;       // 공구 제목
    @CreationTimestamp
    private Timestamp regDate;  // 아이템 등록 시간
    private Integer likeNum;    // 좋아요 수
    private Integer totalNum;   // 현재까지 구매한 수량
    private String location;    // 대략적 위치
    private Integer nowTab;         // 현재 공구 진행 상황

    @OneToOne(mappedBy = "item", cascade = CascadeType.MERGE)
    private ItemTab1 itemTab1;

    @OneToOne(mappedBy = "item", cascade = CascadeType.MERGE)
    private ItemTab2 itemTab2;

    @OneToOne(mappedBy = "item", cascade = CascadeType.MERGE)
    private ItemTab4 itemTab4;

    @OneToOne(mappedBy = "item", cascade = CascadeType.MERGE)
    private ItemTab5 itemTab5;
}
