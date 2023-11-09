package com.kusitms.jipbap.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterStoreRequestDto {

    private String name;
    private String description;
    private Boolean koreanYn;
    private Long minOrderAmount;
}
