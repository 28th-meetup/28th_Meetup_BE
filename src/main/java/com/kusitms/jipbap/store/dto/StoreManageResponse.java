package com.kusitms.jipbap.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreManageResponse {
    private Long processingOrderCount;
    private Long todayOrderCount;
}
