package com.kusitms.jipbap.order.model.entity;

import com.kusitms.jipbap.order.exception.OrderStatusFromStringError;

public enum OrderStatus {
    PENDING, // 판매자에게 확인 중
    ACCEPTED, // 판매자가 주문 수락
    REJECTED, // 판매자가 주문 거절
    COMPLETED; // 완료됨

    public static OrderStatus fromString(String text) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new OrderStatusFromStringError("No constant with text " + text + " found in OrderStatus enum");
    }
}