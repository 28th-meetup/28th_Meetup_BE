package com.kusitms.jipbap.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchStoreResponseDto {

    private Long id;
    private String name;
    private String description;
    private Boolean koreanYn;
    private Double avgRate;
    private Long minOrderAmount;
    private String image;

    private Boolean isBookmarked;
    private Long lastId;
}
