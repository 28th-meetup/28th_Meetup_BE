package com.kusitms.jipbap.auth.dto;

import com.kusitms.jipbap.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
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
    private String address;
    @NotBlank
    private String phoneNum;
    @NotBlank
    private Role role;

    private String imageUrl;

}
