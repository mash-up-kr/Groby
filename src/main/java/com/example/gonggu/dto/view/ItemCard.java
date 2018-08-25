package com.example.gonggu.dto.view;

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
    private String likeNum;
    private String title;
    private int amountLimit;
    private int participantNum;
    private int progress;
    private int nowTab;
    private int participantPercent;
    private Date endDate;
    private Date dueDate;

}
