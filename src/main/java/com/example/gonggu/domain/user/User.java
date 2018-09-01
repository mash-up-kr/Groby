package com.example.gonggu.domain.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"participants" ,"userPw"})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(length = 100)
    private String userEmail;
    private String userPw;
    @Column(length = 50)
    private String userName;

    @Column(length = 50)
    private String accountNum;
    @Column(length = 50)
    private String accountBank;
    @Column(length = 50)
    private String accountHolder;

    // 유저 default 설정
    private Boolean isDeleted = false;

    private String userToken;

    @CreationTimestamp
    private Timestamp regDate;
    @UpdateTimestamp
    private Timestamp updateDate;

    @OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<ListOfParticipantForUser> participants;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Notification> notifications;
}
