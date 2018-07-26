package com.example.gonggu.domain.item;

import com.example.gonggu.domain.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(exclude = "user")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
    private User user;

    private String category;
    private String title;
    private Date regDate;
    private int likeNum;
    private int totalNum;
    private String location;
}
