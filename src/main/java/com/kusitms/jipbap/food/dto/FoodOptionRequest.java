package com.kusitms.jipbap.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodOptionRequest {
    private String name;

    private Long dollarPrice;

    private Long canadaPrice;
}
