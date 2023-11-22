package com.kusitms.jipbap.user.controller;

import com.kusitms.jipbap.auth.dto.ReissueResponseDto;
import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import com.kusitms.jipbap.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "액세스 토큰 재발급 - 헤더에 refreshToken 정보 포함하여 요청")
    @PostMapping("/reissue")
    public CommonResponse<ReissueResponseDto> reissue(@Auth AuthInfo authInfo) {
        return new CommonResponse<>(userService.reissue(authInfo.getEmail(), authInfo.getToken()));
    }

    @Operation(summary = "로그아웃 - 리프레쉬 토큰 삭제")
    @PostMapping("/logout")
    public CommonResponse<String> logout(@Auth AuthInfo authInfo) {
        userService.logout(authInfo.getEmail());
        return new CommonResponse<>("로그아웃 성공");
    }

    @Operation(summary = "유저 닉네임 정보 받아오기")
    @GetMapping("/nickname")
    public CommonResponse<String> getUserNickname(@Auth AuthInfo authInfo) {
        return new CommonResponse<>(userService.getUserNickname(authInfo.getEmail()));
    }
}

