package com.example.gonggu.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notiId;
    @Column(length = 50)
    private String title;
    @Column(length = 100)
    private String contents;

    @Column(length = 150)
    private String itemTitle;
    private Long itemId;

    @CreationTimestamp
    private Timestamp regDate;


}
