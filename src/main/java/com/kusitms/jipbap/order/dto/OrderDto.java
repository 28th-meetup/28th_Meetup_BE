package com.kusitms.jipbap.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private Long user;

    private Long food;

    private Long orderCount;
}
