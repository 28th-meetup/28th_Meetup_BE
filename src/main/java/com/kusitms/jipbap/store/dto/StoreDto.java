package com.kusitms.jipbap.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

    private Long id;
    private String name;
    private String description;
    private Boolean koreanYn;
    private Double avgRate; // 가게 평점
    private Double minOrderAmount; //최소 주문 금액
    private String[] images;
}
