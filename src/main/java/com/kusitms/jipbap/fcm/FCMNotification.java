package com.kusitms.jipbap.fcm;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FCMNotification {
    private String title;
    private String body;
    // private String image;
    // private Map<String, String> data;
}
