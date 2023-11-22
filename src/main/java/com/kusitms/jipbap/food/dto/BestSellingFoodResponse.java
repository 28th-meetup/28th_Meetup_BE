package com.kusitms.jipbap.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BestSellingFoodResponse {
    private Long foodId;
    private String name;
    private String storeName;
    private Double dollarPrice;
    private Double canadaPrice;
    private Double avgRate;

    public BestSellingFoodResponse(Long foodId, String name, String storeName, Double dollarPrice, Double canadaPrice, Double avgRate) {
        this.foodId = foodId;
        this.name = name;
        this.storeName = storeName;
        this.dollarPrice = roundToTwoDecimals(dollarPrice);
        this.canadaPrice = roundToTwoDecimals(canadaPrice);
        this.avgRate = roundToTwoDecimals(avgRate);
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100) / 100.0;
    }
}
