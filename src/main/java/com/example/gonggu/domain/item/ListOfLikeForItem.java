package com.example.gonggu.domain.item;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class ListOfLikeForItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likeId;

    @Column(length = 100)
    private String userEmail;

}
