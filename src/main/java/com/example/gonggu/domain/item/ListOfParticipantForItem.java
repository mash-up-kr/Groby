package com.example.gonggu.domain.item;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class ListOfParticipantForItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long participantId;

//    private Long itemId;
    @Column(length = 50)
    private String userName;            // user의 닉네임
    //default 0 설정
    private Integer userPermission;     // Tab2[ 0:default / 1:승인 / 2:보류 / 3:취소 ] // Tab5[ 11:배부완료 / 12:배부안됨 ]
    @Column(length = 50)
    private String accountNum;
    @Column(length = 50)
    private String accountBank;
    @Column(length = 50)
    private String accountHolder;
    private Integer amount;
    private Integer price;
    @Column(length = 1000)
    private String optionString;
}
