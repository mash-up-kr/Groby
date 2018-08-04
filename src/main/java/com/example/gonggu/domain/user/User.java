package com.example.gonggu.domain.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNum;
    private String userId;
    private String userPW;
    private String userName;

    private String accountNum;
    private String accounBank;
    private String accountHolder;

    @CreationTimestamp
    private Timestamp regDate;
    @UpdateTimestamp
    private Timestamp updateDate;
}
