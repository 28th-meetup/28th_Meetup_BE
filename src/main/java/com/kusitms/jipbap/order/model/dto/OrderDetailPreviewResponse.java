package com.kusitms.jipbap.order.model.dto;

import com.kusitms.jipbap.order.model.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailPreviewResponse {
    private Long orderDetailId;
    private Long foodId;
    private String foodName;
    private Long foodOptionId;
    private String foodOptionName;
    private Long orderCount;

    public OrderDetailPreviewResponse(OrderDetail orderDetail){
        this.orderDetailId = orderDetail.getId();
        this.foodId = orderDetail.getFood().getId();
        this.foodName = orderDetail.getFood().getName();
        this.foodOptionId = orderDetail.getFoodOption().getId();
        this.foodOptionName = orderDetail.getFoodOption().getName();
        this.orderCount = orderDetail.getOrderCount();
    }

}
