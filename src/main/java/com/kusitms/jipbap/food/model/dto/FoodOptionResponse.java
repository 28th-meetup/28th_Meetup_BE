package com.kusitms.jipbap.food.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodOptionResponse {
    private Long id;
    private String name;
    private Double dollarPrice;
    private Double canadaPrice;
}
