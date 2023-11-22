package com.kusitms.jipbap.chat.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kusitms.jipbap.chat.model.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown =true)
public class MessageResponseDto {
    private Long id;
    private String roomName;
    private String sender;
    private String roomId;
    private String receiver;
    private String message;
    private String lastMessageSentTime; // (채팅방 조회시) 메세지 마지막 전송 시각

    // 채팅방 생성
    public MessageResponseDto(Room room) {
        this.id = room.getId();
        this.roomName = room.getRoomName();
        this.sender = room.getSenderName();
        this.roomId = room.getRoomId();
        this.receiver = room.getReceiverName();
    }

    // 사용자 관련 채팅방 전체 조회
    public MessageResponseDto(Long id, String roomName, String roomId, String sender, String receiver) {
        this.id = id;
        this.roomName = roomName;
        this.sender = sender;
        this.roomId = roomId;
        this.receiver = receiver;
    }

    public MessageResponseDto(String roomId) {
        this.roomId = roomId;
    }

    public void setLatestMessageContent(String message) {
        this.message = message;
    }

    public void setLatestMessageCreatedAt(String createdAt) {
        this.lastMessageSentTime = createdAt;
    }
}