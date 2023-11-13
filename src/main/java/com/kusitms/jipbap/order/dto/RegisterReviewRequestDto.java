package com.kusitms.jipbap.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterReviewRequestDto {
    private Long orderId;
    private Long rating;
    private String message;
}
