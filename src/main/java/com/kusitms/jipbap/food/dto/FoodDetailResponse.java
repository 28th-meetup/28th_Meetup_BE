package com.kusitms.jipbap.food.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDetailResponse {
    private Long id;
    private Long storeId;
    private String storeName;
    private Long categoryId;
    private String name;
    private Double dollarPrice;
    private Double canadaPrice;
    private String image;
    private String description;
    private String foodPackage;
    private String informationDescription;
    private String ingredient;
    private Double minOrderAmount; // 최소 주문 금액 추가
    private List<FoodOptionResponse> foodOptionResponseList;
}
