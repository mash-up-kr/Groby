package com.example.gonggu.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ItemLikeJson {
    @NotNull
    private String itemId;
    @NotNull
    private String userEmail;
}
