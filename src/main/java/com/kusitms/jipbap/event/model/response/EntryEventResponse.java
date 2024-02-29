package com.kusitms.jipbap.event.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntryEventResponse {
    private String userEmail;
    private String title;
    private Long sequence;
}
