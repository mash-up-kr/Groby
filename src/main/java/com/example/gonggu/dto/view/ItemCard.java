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
    private String title;
    private int nowTab;
    private String likeNum;             // T1
    private int progress;               // T2
    private int amountLimit;            // T2
    private int participantNum;         // T2
    private int participantPercent;     // T2
    private Date dueDate;
//    private Date endDate;

}
