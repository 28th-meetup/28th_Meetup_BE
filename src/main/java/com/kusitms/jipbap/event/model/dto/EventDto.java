package com.kusitms.jipbap.event.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private String description;
    private Long amount;
}
