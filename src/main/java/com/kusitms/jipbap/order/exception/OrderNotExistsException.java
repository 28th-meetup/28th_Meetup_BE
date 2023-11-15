package com.kusitms.jipbap.order.exception;

public class OrderNotExistsException extends RuntimeException {
    public OrderNotExistsException(String message) {
        super(message);
    }
}
