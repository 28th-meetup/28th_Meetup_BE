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
    private Long foodOptionId; //foodOptionId로 설정
    private Long orderCount; //품목 개수
    private Long orderAmount; //품목당 가격

    public OrderFoodDetailResponse(OrderDetail orderDetail){
        System.out.println(orderDetail.getId());
        this.orderDetailId = orderDetail.getId();
        this.foodId = orderDetail.getFood().getId();
        this.foodOptionId = orderDetail.getFoodOptionId();
        this.orderCount = orderDetail.getOrderCount();
        this.orderAmount = orderDetail.getOrderAmount();
    }
}
