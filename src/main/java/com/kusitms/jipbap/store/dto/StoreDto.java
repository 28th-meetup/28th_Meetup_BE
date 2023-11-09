package com.kusitms.jipbap.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDto {

    private Long id;
    private String name;
    private String description;
    private Boolean koreanYn;
    private Double avgRate;
    private Long bookmarkCount;
    private String image;
}
