package com.kusitms.jipbap.store.dto;

import com.kusitms.jipbap.store.Store;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInfoDto {
    private Long id;
    private String name;
    private String description;
    private Boolean koreanYn;
    private Double avgRate; // 가게 평점
    private Double minOrderAmount; //최소 주문 금액
    private String[] images;
    private String address;
    private String detailAddress;
    private String phoneNum;
    private String deliveryRegion;
    private String operationTime;

    public StoreInfoDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.description = store.getDescription();
        this.koreanYn = store.getKoreanYn();
        this.avgRate = roundToTwoDecimals(store.getAvgRate());
        this.minOrderAmount = roundToTwoDecimals(store.getMinOrderAmount());
        this.images = new String[]{store.getImage(), store.getImage2(), store.getImage3()};
        this.address = store.getAddress();
        this.detailAddress = store.getDetailAddress();
        this.phoneNum = store.getPhoneNum();
        this.deliveryRegion = store.getDeliveryRegion();
        this.operationTime = store.getOperationTime();
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100) / 100.0;
    }

}
