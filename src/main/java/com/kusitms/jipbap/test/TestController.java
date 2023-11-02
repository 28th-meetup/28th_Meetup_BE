package com.kusitms.jipbap.test;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * 커넥션 테스트용 컨트롤러
     * @return 성공시 String 반환
     */
    @Operation(summary = "ec2 서버 커넥션 테스트용 컨트롤러")
    @GetMapping("/test")
    public String test() {
        return "Healthy Connection";
    }
}
