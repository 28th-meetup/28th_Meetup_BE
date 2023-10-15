package com.kusitms.jipbap.auth;

import com.kusitms.jipbap.auth.dto.*;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "일반 회원 가입")
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public void signUp(@Valid @RequestBody SignUpRequestDto dto) {
        authService.signUp(dto);
    }

    @Operation(summary = "로그인")
    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponseDto signIn(@Valid @RequestBody SignInRequestDto dto) {
        return authService.signIn(dto.getEmail(), dto.getPassword());
    }

    @Operation(summary = "카카오 회원 가입(로그인)")
    @PostMapping("/kakao")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponseDto kakaoVerification(@RequestBody KakaoSignInRequestDto dto) {
        return authService.kakaoAutoSignIn(authService.getKakaoProfile(dto.getToken()));
    }

    @Operation(summary = "액세스 토큰 재발급 - 헤더에 refreshToken 정보 포함하여 요청")
    @PostMapping("/reissue")
    public ReissueResponseDto reissue(@Auth AuthInfo authInfo) {
        return authService.reissue(authInfo.getEmail(), authInfo.getToken());
    }

}
