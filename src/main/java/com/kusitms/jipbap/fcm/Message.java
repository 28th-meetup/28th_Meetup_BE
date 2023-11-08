package com.kusitms.jipbap.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
public class Message {
    private FCMNotification notification;
    private String token;
}
