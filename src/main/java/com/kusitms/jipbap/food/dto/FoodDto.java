package com.kusitms.jipbap.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FoodDto {

    private Long id;
    private Long storeId;
    private Long categoryId;
    private String name;
    private Long price;
    private String description;
}
