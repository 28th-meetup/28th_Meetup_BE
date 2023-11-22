package com.kusitms.jipbap.order.dto;

import com.kusitms.jipbap.order.Order;
import com.kusitms.jipbap.order.OrderDetail;
import com.kusitms.jipbap.order.Review;
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
public class OrderHistoryResponse {
    private Long id;
    private Long userId;
    private Long storeId;
    private String storeName;
    private String storeImage;
    private String storeImage2;
    private String storeImage3;
    private Long totalCount;
    private Double totalPrice;
    private String selectedOption; //배달 포장
    private String orderedAt;
    private String status;
    private Boolean isReviewed; //리뷰 작성 여부
    private List<OrderFoodDetailResponse> orderFoodDetailList;

    public OrderHistoryResponse(Order order){
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.storeId = order.getStore().getId();
        this.storeName = order.getStore().getName();
        this.storeImage = order.getStore().getImage();
        this.storeImage2 = order.getStore().getImage2();
        this.storeImage3 = order.getStore().getImage3();
        this.totalCount = order.getTotalCount();
        this.totalPrice = roundToTwoDecimals(order.getTotalPrice());
        this.selectedOption = order.getSelectedOption();
        this.orderedAt = setOrderedAt(order.getCreatedAt());
        this.orderFoodDetailList = setOrderFoodDetailList(order.getOrderDetail());
        this.status = order.getStatus().toString();
        this.isReviewed = isReviwed(order.getReview());
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

    private Boolean isReviwed(Review review) {
        if (review == null) {
            return false;
        }
        return true;
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100) / 100.0;
    }

}
