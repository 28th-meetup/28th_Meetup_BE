package com.kusitms.jipbap.order.dto;


import com.kusitms.jipbap.order.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFoodDetailResponse {
    private Long orderDetailId; //고유 pk
    private Long foodId;
    private String foodName;
    private Long foodOptionId; //foodOptionId로 설정
    private String foodOptionName;
    private Long orderCount; //품목 개수
    private Double orderAmount; //품목당 가격

    public OrderFoodDetailResponse(OrderDetail orderDetail){
        this.orderDetailId = orderDetail.getId();
        this.foodId = orderDetail.getFood().getId();
        this.foodName = orderDetail.getFood().getName();
        this.foodOptionId = orderDetail.getFoodOption().getId();
        this.foodOptionName = orderDetail.getFoodOption().getName();
        this.orderCount = orderDetail.getOrderCount();
        this.orderAmount = roundToTwoDecimals(orderDetail.getOrderAmount());
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100) / 100.0;
    }
}
