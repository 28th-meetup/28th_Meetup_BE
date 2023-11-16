package com.kusitms.jipbap.user;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import com.kusitms.jipbap.user.dto.NicknameValidateRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그아웃 - 리프레쉬 토큰 삭제")
    @PostMapping("/logout")
    public CommonResponse<String> logout(@Auth AuthInfo authInfo) {
        userService.logout(authInfo.getEmail());
        return new CommonResponse<>("로그아웃 성공");
    }

    @Operation(summary = "닉네임 중복 여부 확인")
    @PostMapping("/nickname")
    public CommonResponse<String> checkNicknameIsDuplicate(@RequestBody NicknameValidateRequest dto) {
        return new CommonResponse<>(userService.checkNicknameIsDuplicate(dto.getNickname()));
    }
}

