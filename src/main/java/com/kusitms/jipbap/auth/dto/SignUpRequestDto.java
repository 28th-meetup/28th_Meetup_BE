package com.kusitms.jipbap.auth.dto;

import com.kusitms.jipbap.user.CountryPhoneCode;
import com.kusitms.jipbap.user.Role;
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
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    @NotBlank
    private CountryPhoneCode countryPhoneCode;
    @NotBlank
    private String phoneNum;
    @NotBlank
    private Role role;
    private String imageUrl;
}
