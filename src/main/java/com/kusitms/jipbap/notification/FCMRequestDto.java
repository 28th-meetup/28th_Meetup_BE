package com.kusitms.jipbap.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FCMRequestDto {
    Long userId;
    String title;
    String body;
}
