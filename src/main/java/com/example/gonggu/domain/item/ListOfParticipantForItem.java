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
public class ListOfParticipantForItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long participantId;

//    private Long itemId;
    private String userName;            // user의 닉네임
    private Integer userPermission;     // 0:취소 / 1:확인 / 2:보류
    private String accountNum;
    private String accountBank;
    private String accountHolder;
    private Integer amount;
    private Integer price;
}
