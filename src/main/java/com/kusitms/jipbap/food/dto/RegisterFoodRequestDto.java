package com.kusitms.jipbap.food.dto;

import com.kusitms.jipbap.food.Category;
import com.kusitms.jipbap.food.FoodOption;
import com.kusitms.jipbap.food.FoodRepository;
import com.kusitms.jipbap.store.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFoodRequestDto {

    private Long storeId;
    private Long categoryId;
    private String name;
    private Long dollarPrice;
    private Long canadaPrice;
    private String description;
    private List<FoodOptionRequest> foodOptionRequestList;
    private String foodPackage;

}
