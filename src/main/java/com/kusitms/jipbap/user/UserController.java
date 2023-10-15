package com.kusitms.jipbap.user;

import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그아웃 - 리프레쉬 토큰 삭제")
    @PostMapping("/logout")
    public void logout(@Auth AuthInfo authInfo) {
        userService.logout(authInfo.getEmail());
    }
}

