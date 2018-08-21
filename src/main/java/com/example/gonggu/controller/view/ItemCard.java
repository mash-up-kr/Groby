package com.example.gonggu.controller.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ItemCard {
    private String thumnailURL;
    private String itemId;
    private int progress;
    private String likeNum;
    private Date endDate;
    private String title;
    private int nowTab;

}
