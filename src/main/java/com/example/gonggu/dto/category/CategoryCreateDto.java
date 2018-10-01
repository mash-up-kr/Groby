package com.example.gonggu.dto.category;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryCreateDto {
    @NotNull(message = "카테고리 이름을 넣어주세요")
    private String category;
}
