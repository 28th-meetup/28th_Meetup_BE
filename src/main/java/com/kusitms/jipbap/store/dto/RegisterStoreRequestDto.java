package com.kusitms.jipbap.store.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class RegisterStoreRequestDto {

    private String name;
    private String description;
    private Boolean koreanYn;
    private Long minOrderAmount;
}
