package com.kusitms.jipbap.food.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFoodRequestDto {

    @NotBlank
    private Long categoryId;
    @NotBlank
    private String name;
    private Double dollarPrice;
    private Double canadaPrice;
    @NotBlank
    private String description;
    private List<FoodOptionRequest> foodOptionRequestList;
    @NotBlank
    private String foodPackage;
    @NotBlank
    private String ingredient;

}
