package com.example.gonggu.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_num", nullable = false)
    private Long userNum;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pw")
    private String userPW;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "account_num")
    private String accountNum;

    @Column(name = "account_bank")
    private String accounBank;


    @Column(name = "account_holder")
    private String accountHolder;

    @Column(name = "reg_date")
    @CreationTimestamp
//    private LocalDateTime regDate;
    private Timestamp regDate;

    @Column(name = "update_date")
    @UpdateTimestamp
//    private LocalDateTime updateDate;
    private Timestamp updateDate;




}
