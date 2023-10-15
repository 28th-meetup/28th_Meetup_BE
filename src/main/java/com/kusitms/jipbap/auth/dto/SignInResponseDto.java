package com.kusitms.jipbap.auth.dto;

import com.kusitms.jipbap.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private Long id;
    private String email;
    private String image;
    private Role role;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenRemainTime;
    private Long refreshTokenRemainTime;
}
