package com.example.gonggu.domain.item;

import com.example.gonggu.domain.category.Category;
import com.example.gonggu.domain.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"user", "category","itemTab1", "itemTab2", "itemTab3" ,"itemTab4", "itemTab5" , "category","participantForItemList","likeForItemList","imgPaths"})
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;        // 카테고리
    @Column(length = 150)
    private String title;           // 공구 제목
    @CreationTimestamp
    private Timestamp regDate;      // 아이템 등록 시간
    @UpdateTimestamp
    private Timestamp updateDate;
    private Integer numOfLike;      // 좋아요 수
    private Integer numOfOrder;     // 현재까지 구매한 수량
    private Integer nowTab;         // 현재 공구 진행 상황
    private Integer amountLimit;    // 공구 오픈 최소 수량
    private Boolean isDeleted;      // tab1에서 삭제할 경우
    private String thumnail;
    @Column(length = 500)
    private String orderList;       // 옵션별 주문리스트 / S 빨강:1000:10 / S 노랑:1500:50 / S 파랑:1200:20


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tab1_id")
    private ItemTab1 itemTab1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tab2_id")
    private ItemTab2 itemTab2;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tab3_id")
    private ItemTab3 itemTab3;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tab4_id")
    private ItemTab4 itemTab4;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tab5_id")
    private ItemTab5 itemTab5;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<ListOfParticipantForItem> participantForItemList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<ListOfLikeForItem> likeForItemList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<ItemImgPath> imgPaths;

}
