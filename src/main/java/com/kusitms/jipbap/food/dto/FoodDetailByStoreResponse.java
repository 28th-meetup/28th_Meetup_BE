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
    private Long price;
    private String description;
    private Long recommendCount;

    public FoodDetailByStoreResponse(Food food){
        this.id = food.getId();
        this.categoryId = food.getCategory().getId();
        this.name = food.getName();
        this.price = food.getPrice();
        this.description = food.getDescription();
        this.recommendCount = food.getRecommendCount();
    }
}
