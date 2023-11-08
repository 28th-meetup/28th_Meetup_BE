package com.kusitms.jipbap.fcm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FCMMessage {
    @Key("validate_only")
    @JsonIgnore
    private boolean validateOnly;

    @Key("message")
    private Message message;

    @Builder
    public FCMMessage(boolean validateOnly, Message message) {
        this.validateOnly = validateOnly;
        this.message = message;
    }
}
