package com.kusitms.jipbap.order.model.dto;

import com.kusitms.jipbap.order.model.entity.Order;
import com.kusitms.jipbap.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPreviewResponse {
    private Long orderId;
    private String userName;
    private String addressAndPostalCode;
    private String detailAddress;
    private String selectedOption;
    private String orderedAt;
    private List<OrderDetailPreviewResponse> orderDetailPreviewList;

    public OrderPreviewResponse(Order order) {
        this.orderId = order.getId();
        this.userName = order.getUser().getUsername();
        this.addressAndPostalCode = setAddress(order.getUser());
        this.detailAddress = order.getUser().getDetailAddress();
        this.selectedOption = order.getSelectedOption();
        this.orderedAt = setOrderedAt(order.getCreatedAt());
        this.orderDetailPreviewList = order.getOrderDetail().stream()
                .map(OrderDetailPreviewResponse::new)
                .collect(Collectors.toList());
    }

    private String setAddress(User user){
        return user.getAddress() + " " + user.getGlobalRegion().getRegionName() + " " + user.getGlobalRegion().getCountryShortName() + " " + user.getPostalCode();
    }
    private String setOrderedAt(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
