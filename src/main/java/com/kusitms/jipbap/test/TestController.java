package com.kusitms.jipbap.test;

import com.kusitms.jipbap.auth.exception.InvalidEmailException;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;

    /**
     * 커넥션 테스트용 컨트롤러
     * @return 성공시 String 반환
     */
    @Operation(summary = "ec2 서버 커넥션 테스트용 컨트롤러")
    @GetMapping
    public String test() {
        return "Healthy Connection";
    }

    @Operation(summary = "ec2 서버 커넥션 테스트용 컨트롤러(인증)")
    @GetMapping("/auth")
    public String test2(@Auth AuthInfo authInfo) {
        User user = userRepository.findByEmail(authInfo.getEmail()).orElseThrow(()->new InvalidEmailException("회원정보가 존재하지 않습니다."));
        return "Healthy Connection";
    }
}
