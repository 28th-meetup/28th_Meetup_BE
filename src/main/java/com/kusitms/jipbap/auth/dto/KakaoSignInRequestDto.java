package com.kusitms.jipbap.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoSignInRequestDto {
    @NotBlank
    private String token;
}
