package com.example.gonggu.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@ToString
@Entity
public class ItemTab3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tabThreeId;

    private String contents;
}
