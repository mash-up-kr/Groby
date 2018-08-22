package com.example.gonggu.domain.category;

import com.example.gonggu.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categotyId;
    @Column(length = 50)
    private String category;
    // 카테고리 목록
    // 1 의류
    // 2 잡화
    // 3 식품
    // 4 화장품
    // 5 기타
}
