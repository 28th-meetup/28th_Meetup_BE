package com.kusitms.jipbap.order.exception;

public class OrderStatusFromStringError extends RuntimeException {
    public OrderStatusFromStringError(String message) {
        super(message);
    }
}
