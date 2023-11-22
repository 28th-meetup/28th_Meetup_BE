package com.kusitms.jipbap.chat.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kusitms.jipbap.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown =true)
public class RoomDto implements Serializable {

    private static final long serialVersionUID = 298188411555580374L;       // Redis 에 저장되는 객체들이 직렬화가 가능하도록

    private Long id;
    private String roomName;    // 채팅방 제목
    private String roomId;
    private String senderName;     // 메시지 송신자 이름
    private String receiverName;   // 메시지 수신자 이름

    // 채팅방 생성
    private RoomDto() {

    }
    public static RoomDto create(String receiverName, User user) {
        RoomDto roomDto = new RoomDto();
        roomDto.roomName = receiverName;
        roomDto.roomId = UUID.randomUUID().toString();
        roomDto.senderName = user.getUsername();
        roomDto.receiverName = receiverName;

        return roomDto;
    }

}