package com.kusitms.jipbap.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodDetailResponse {
    private Long id;
    private Long storeId;
    private Long categoryId;
    private String name;
    private Long dollarPrice;
    private Long canadaPrice;
    private String description;
    private String image;
    private List<FoodOptionResponse> foodOptionResponseList;
}
