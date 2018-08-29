package com.example.gonggu.dto.user;

import com.example.gonggu.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotiContents {
    private User usr;
    private Long itemId;
    private String itemTitle;
    private String title;
    private String contents;

}
