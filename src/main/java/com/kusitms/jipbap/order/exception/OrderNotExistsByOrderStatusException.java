package com.kusitms.jipbap.order.exception;

public class OrderNotExistsByOrderStatusException extends RuntimeException {
    public OrderNotExistsByOrderStatusException(String message) {
        super(message);
    }
}
