package com.kusitms.jipbap.event.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterEventResponse {
    private Long id;
    private String title;
    private String description;
    private Long amount;
}
