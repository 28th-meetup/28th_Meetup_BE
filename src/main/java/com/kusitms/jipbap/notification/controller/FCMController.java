package com.kusitms.jipbap.notification.controller;

import com.kusitms.jipbap.notification.service.FCMNotificationService;
import com.kusitms.jipbap.notification.model.dto.FCMRequestDto;
import com.kusitms.jipbap.notification.model.dto.FCMValidRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FCMController {

    private final FCMNotificationService fcmNotificationService;

    @PostMapping("/send/message")
    public String sendNotificationByToken (@RequestBody FCMRequestDto dto) {
        String answer = fcmNotificationService.sendNotificationByToken(dto);
        return answer;
    }

    @PostMapping("/valid/fcm")
    public String isValidFcmToken(@RequestBody FCMValidRequestDto dto) {
        Boolean ans = fcmNotificationService.isValidToken(dto.getFcmToken());
        if(ans) return "True";
        else return "False";
    }

}