package com.example.gonggu.domain.user;

import com.example.gonggu.domain.item.Item;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Setter
@Getter
@ToString(exclude = "items")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNum;
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;

    private String accountNum;
    private String accountBank;
    private String accountHolder;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Item> items;
}