package com.kusitms.jipbap.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {
    @Email
    @NotBlank
    @Schema(description = "이메일", example = "goodpoint@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "비밀번호", example = "1234abcd")
    private String password;
}
