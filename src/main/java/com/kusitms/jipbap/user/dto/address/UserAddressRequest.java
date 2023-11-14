package com.kusitms.jipbap.user.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressRequest {
    Long userId;
    Long globalRegionId;
    String address;
    String detailAddress;
    String postalCode;
}
