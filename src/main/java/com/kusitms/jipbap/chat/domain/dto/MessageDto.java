package com.kusitms.jipbap.chat.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kusitms.jipbap.chat.domain.entity.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {
    private String senderName;
    private String roomId;
    private String message;
    private String sentTime;

    // 대화 조회
    public MessageDto(Message message) {
        this.senderName = message.getSenderName();
        this.roomId = message.getRoom().getRoomId();
        this.message = message.getMessage();
    }
}