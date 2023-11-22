package com.kusitms.jipbap.store.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetailResponseDto {

    private StoreDto storeDto;
    private Boolean isBookmarked;
    private Boolean isFoodChangeable;
}
