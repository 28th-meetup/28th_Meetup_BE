package com.kusitms.jipbap.order.dto;

import com.kusitms.jipbap.order.Order;
import com.kusitms.jipbap.order.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFoodResponse {
    private Long id;
    private Long userId;
    private Long storeId;
    private Long totalCount;
    private Double totalPrice;
    private String selectedOption;
    private String orderedAt;
    private String status;
    private List<OrderFoodDetailResponse> orderFoodDetailList;

    public OrderFoodResponse(Order order){
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.storeId = order.getStore().getId();
        this.totalCount = order.getTotalCount();
        this.totalPrice = roundToTwoDecimals(order.getTotalPrice());
        this.selectedOption = order.getSelectedOption();
        this.orderedAt = setOrderedAt(order.getCreatedAt());
        this.orderFoodDetailList = setOrderFoodDetailList(order.getOrderDetail());
        this.status = order.getStatus().toString();
    }
    private String setOrderedAt(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    private List<OrderFoodDetailResponse> setOrderFoodDetailList(List<OrderDetail> orderDetailList) {
       return orderDetailList.stream().map(item -> {
            return new OrderFoodDetailResponse(item);
        }).collect(Collectors.toList());
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100) / 100.0;
    }

}
