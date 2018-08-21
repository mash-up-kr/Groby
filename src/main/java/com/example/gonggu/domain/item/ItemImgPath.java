package com.example.gonggu.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
public class ItemImgPath {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemImgPathId;
    private Integer tab;
    private String img_path;
}
