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
public class HomeResponseDto {
    private Long globalRegionId;
    List<FoodPreviewResponse> bestSellingFoodList;
    List<FoodPreviewResponse> recentSetFoodList;
}
