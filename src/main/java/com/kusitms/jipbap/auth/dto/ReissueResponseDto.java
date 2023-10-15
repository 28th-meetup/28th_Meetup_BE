package com.kusitms.jipbap.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReissueResponseDto {
    private String accessToken;
    private String refreshToken;
}
