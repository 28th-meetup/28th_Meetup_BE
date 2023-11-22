package com.kusitms.jipbap.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFoodDetailRequest {
    private Long foodId;
    private Long foodOptionId;
    private Long orderCount;
    private Double orderAmount;
}
