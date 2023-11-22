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
public class OwnerOrderStatusResponse {
    private int orderCount;
    private List<OrderPreviewResponse> orderPreviewResponseList;
}
