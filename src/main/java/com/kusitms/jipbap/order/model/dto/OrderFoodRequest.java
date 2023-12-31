package com.kusitms.jipbap.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFoodRequest {
    private Long store;
    private Double totalPrice;
    private Long totalCount;
    private String selectedOption;
    private List<OrderFoodDetailRequest> orderFoodDetailList;
}
