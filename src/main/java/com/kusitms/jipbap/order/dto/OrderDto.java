package com.kusitms.jipbap.order.dto;

import com.kusitms.jipbap.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long user;
    //private Long food;
    //private Long orderCount;
    private Long totalPrice;
    private String orderedAt;
    private String status;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.user = order.getUser().getId();
//        this.food = order.getFood().getId();
//        this.orderCount = order.getOrderCount();
        this.totalPrice = order.getTotalPrice();
        this.orderedAt = setOrderedAt(order.getCreatedAt());
        this.status = order.getStatus().toString();
    }

    private String setOrderedAt(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
