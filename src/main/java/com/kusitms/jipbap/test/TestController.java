package com.kusitms.jipbap.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * 커넥션 테스트용 컨트롤러
     * @return 성공시 String 반환
     */
    @GetMapping("/test")
    public String test() {
        return "Connection Success";
    }
}
