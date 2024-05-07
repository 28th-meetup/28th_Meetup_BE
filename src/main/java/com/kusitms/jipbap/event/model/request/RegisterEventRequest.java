package com.kusitms.jipbap.event.model.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RegisterEventRequest {
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private Long amount;
}
