package com.kusitms.jipbap.food.dto;


import com.kusitms.jipbap.food.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodDetailByStoreResponse {
    private Long id;
    private Long categoryId;
    private String name;
    private Double dollarPrice;
    private Double canadaPrice;
    private String description;
    private Long recommendCount;
    private String image;

    public FoodDetailByStoreResponse(Food food){
        this.id = food.getId();
        this.categoryId = food.getCategory().getId();
        this.name = food.getName();
        this.dollarPrice = roundToTwoDecimals(food.getDollarPrice());
        this.canadaPrice = roundToTwoDecimals(food.getCanadaPrice());
        this.description = food.getDescription();
        this.recommendCount = food.getRecommendCount();
        this.image = food.getImage();
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100) / 100.0;
    }
}
