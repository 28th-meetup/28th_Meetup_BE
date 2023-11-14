package com.kusitms.jipbap.user.dto.address;

import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.entity.GlobalRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressResponse {
    private Long userId;
    private String email;
    private String username;
    private GlobalRegion globalRegion;
    private String address;
    private String detailAddress;
    private String postalCode;
    private String refreshToken;

    public UserAddressResponse(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.globalRegion = user.getGlobalRegion();
        this.address = user.getAddress();
        this.detailAddress = user.getDetailAddress();
        this.postalCode = user.getPostalCode();
        this.refreshToken = user.getRefreshToken();
    }
}
