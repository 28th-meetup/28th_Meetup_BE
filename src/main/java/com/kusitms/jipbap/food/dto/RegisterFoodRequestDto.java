package com.kusitms.jipbap.food.dto;

import com.kusitms.jipbap.food.Category;
import com.kusitms.jipbap.food.FoodOption;
import com.kusitms.jipbap.food.FoodRepository;
import com.kusitms.jipbap.store.Store;
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
    private Long dollarPrice;
    private Long canadaPrice;
    @NotBlank
    private String description;
    private List<FoodOptionRequest> foodOptionRequestList;
    @NotBlank
    private String foodPackage;
    @NotBlank
    private String ingredient;

}
