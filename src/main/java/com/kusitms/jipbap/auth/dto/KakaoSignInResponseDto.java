package com.kusitms.jipbap.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoSignInResponseDto {
    private SignInResponseDto dto;
    private Boolean isSignUp;
}
