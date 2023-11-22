package com.kusitms.jipbap.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {

    private Long id;
    private Long storeId;
    private Long categoryId;
    private String name;
    private Double dollarPrice;
    private Double canadaPrice;
    private String description;
    private String image;

}
