package com.kusitms.jipbap.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long id;
    private Long orderId;
    private String writerName;
    private String time;
    private String foodName;
    private Long rating;
    private String message;
    private String image;

}
