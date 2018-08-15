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
public class ListOfLikeForItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likeId;

    private String userEmail;

}
