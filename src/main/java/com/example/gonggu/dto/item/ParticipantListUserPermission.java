package com.example.gonggu.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ParticipantListUserPermission {
    @NotNull
    private String itemId;
    @NotNull
    private String listItemId;
    @NotNull
    private int permission;
}
