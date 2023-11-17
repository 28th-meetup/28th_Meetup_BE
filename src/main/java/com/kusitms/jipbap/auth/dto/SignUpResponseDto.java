package com.kusitms.jipbap.auth.dto;

import com.kusitms.jipbap.user.CountryPhoneCode;
import com.kusitms.jipbap.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {
    private Long id;
    private String email;
    private String username;
}
