package com.kusitms.jipbap.store.dto;

import com.kusitms.jipbap.food.dto.FoodDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreFoodResponseDto {

    private List<StoreDetailResponseDto> stores;
    private List<FoodDto> foods;
}
