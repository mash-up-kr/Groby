package com.example.gonggu.domain.user;

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
public class ListOfParticipantForUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long participantId;
    private Long itemId;
    private Boolean owner;
}
