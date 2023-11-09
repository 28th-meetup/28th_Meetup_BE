package com.kusitms.jipbap.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StoreDto {

    private Long id;
    private String name;
    private String description;
    private Boolean koreanYn;
    private Double avgRate; // 가게 평점
    private Long minOrderAmount; //최소 주문 금액
    private String image;
}
