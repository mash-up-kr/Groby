package com.example.gonggu.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
public class ItemTab3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tabThreeId;

    @Column(length = 2000)
    private String contents;
}
