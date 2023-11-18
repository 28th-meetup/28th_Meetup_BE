package com.kusitms.jipbap.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodPreviewResponse {
    private Long foodId;
    private String name;
    private Long storeId;
    private String storeName;
    private Long dollarPrice;
    private Long canadaPrice;
    private String image;
    private Double avgRate;
}
