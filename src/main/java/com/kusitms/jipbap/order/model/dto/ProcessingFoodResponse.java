package com.kusitms.jipbap.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessingFoodResponse {
    private Long foodId;
    private String foodName;
    private Long totalOrderCount;
    private List<ProcessingFoodDetailResponse> processingFoodDetailList;
}
