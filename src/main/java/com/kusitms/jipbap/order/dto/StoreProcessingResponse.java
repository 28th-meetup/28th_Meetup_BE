package com.kusitms.jipbap.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreProcessingResponse {
    int orderCount;
    List<ProcessingFoodResponse> processingFoodList;
}
