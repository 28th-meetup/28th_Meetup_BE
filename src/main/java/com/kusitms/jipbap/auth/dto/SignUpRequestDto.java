package com.kusitms.jipbap.auth.dto;

import com.kusitms.jipbap.user.CountryPhoneCode;
import com.kusitms.jipbap.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    @Email
    @NotBlank
    @Schema(description = "이메일", example = "goodpoint@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "비밀번호", example = "1234abcd")
    private String password;
    @NotBlank
    @Schema(description = "닉네임", example = "조파랑")
    private String username;
    @NotBlank
    @Schema(description = "국가 전화번호 코드", example = "KOREA")
    private CountryPhoneCode countryPhoneCode;
    @NotBlank
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNum;
    @NotBlank
    @Schema(description = "역할", example = "USER")
    private Role role;
    private String imageUrl;
}
