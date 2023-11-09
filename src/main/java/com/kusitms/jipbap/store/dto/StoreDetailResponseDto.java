package com.kusitms.jipbap.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StoreDetailResponseDto {

    private StoreDto storeDto;
    private Boolean isBookmarked;
}
