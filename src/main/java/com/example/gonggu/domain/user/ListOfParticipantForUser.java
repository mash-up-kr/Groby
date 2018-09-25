package com.example.gonggu.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
public class ListOfParticipantForUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long participantId;
    private Boolean owner;

}
