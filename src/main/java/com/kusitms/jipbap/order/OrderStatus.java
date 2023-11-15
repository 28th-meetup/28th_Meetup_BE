package com.kusitms.jipbap.order;

import com.kusitms.jipbap.order.exception.OrderStatusFromStringError;

public enum OrderStatus {
    PENDING, // 대기 중
    CONFIRMED, // 확정
    REJECTED , // 거절됨
    CANCELED, // 취소됨
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