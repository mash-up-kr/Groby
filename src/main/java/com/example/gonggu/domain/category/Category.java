package com.example.gonggu.domain.category;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;
    @Column(length = 50)
    private String category;
    // 카테고리 목록
    // 1 의류
    // 2 잡화
    // 3 식품
    // 4 화장품
    // 5 기타
}
