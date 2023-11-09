package com.kusitms.jipbap.food.dto;

import com.kusitms.jipbap.food.Category;
import com.kusitms.jipbap.store.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterFoodRequestDto {

    private Long storeId;
    private Long categoryId;
    private String name;
    private Long price;
    private String description;
}
