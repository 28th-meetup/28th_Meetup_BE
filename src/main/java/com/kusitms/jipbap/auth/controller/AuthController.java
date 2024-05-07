package com.kusitms.jipbap.auth.controller;

import com.kusitms.jipbap.auth.service.AuthService;
import com.kusitms.jipbap.auth.dto.*;
import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.user.model.dto.NicknameValidateRequest;
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
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto dto) {
        return new CommonResponse<>(authService.signUp(dto));
    }

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<SignInResponseDto> signIn(@Valid @RequestBody SignInRequestDto dto) {
        return new CommonResponse<>(authService.signIn(dto.getEmail(), dto.getPassword()));
    }

    @Operation(summary = "카카오 회원 가입(로그인)")
    @PostMapping("/kakao")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<KakaoSignInResponseDto> kakaoVerification(@RequestBody KakaoSignInRequestDto dto) {
        return new CommonResponse<>(authService.kakaoAutoSignIn(authService.getKakaoProfile(dto.getToken())));
    }

    @Operation(summary = "닉네임 중복 여부 확인")
    @PostMapping("/nickname/duplicate")
    public CommonResponse<String> checkNicknameIsDuplicate(@RequestBody NicknameValidateRequest dto) {
        return new CommonResponse<>(authService.checkNicknameIsDuplicate(dto.getNickname()));
    }

    @Operation(summary = "(테스트용 임시) 관리자 등록")
    @PostMapping("/admin/signup")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<String> adminSignUp(@RequestBody Long id) {
        return new CommonResponse<>(authService.createTmpAdmin(id));
    }

}
