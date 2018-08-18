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
    private Integer userPermission;     // Tab2[ 0:default / 1:승인 / 2:보류 / 3:취소 ] // Tab5[ 11:배부완료 / 12:배부안됨 ]
    private String accountNum;
    private String accountBank;
    private String accountHolder;
    private Integer amount;
    private Integer price;
    private String optionString;
}
