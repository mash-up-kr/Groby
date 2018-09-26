package com.example.gonggu.dto.user;

import com.example.gonggu.dto.view.ItemCard;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReItemListDto {
    private List<ItemCard> ItemList;
}
