package com.kusitms.jipbap.chat.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown =true)
public class MessageRequestDto {
    private Long receiverId;    // 메세지 수신자
    private String receiverName; // 수신자 이름 (채팅방명으로 쓰임)
    private Long storeId;       // 1:1 채팅하기를 시작한 가게 정보
}