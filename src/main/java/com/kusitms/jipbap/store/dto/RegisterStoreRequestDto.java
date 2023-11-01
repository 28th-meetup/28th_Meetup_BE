package com.kusitms.jipbap.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterStoreRequestDto {

    private Long userId;
    private String name;
    private String description;
    private Boolean koreanYn;
    private Double avgRate;
}
