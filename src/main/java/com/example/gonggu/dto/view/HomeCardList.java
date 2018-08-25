package com.example.gonggu.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HomeCardList {
    private List<ItemCard> popularItemList;
    private List<ItemCard> recentItemList;

}
