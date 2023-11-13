package com.kusitms.jipbap.food.dto;

import com.kusitms.jipbap.food.Category;
import com.kusitms.jipbap.store.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFoodRequestDto {

    private Long storeId;
    private Long categoryId;
    private String name;
    private Long price;
    private String description;
}
