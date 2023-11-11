package com.kusitms.jipbap.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFoodRequestDto {
    private Long user;

    private Long food;

    private Long orderCount;
}
