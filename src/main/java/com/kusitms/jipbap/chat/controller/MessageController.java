package com.kusitms.jipbap.chat.controller;


import com.kusitms.jipbap.chat.model.dto.MessageDto;
import com.kusitms.jipbap.chat.service.RoomService;
import com.kusitms.jipbap.chat.service.MessageService;
import com.kusitms.jipbap.chat.service.RedisPublisher;
import com.kusitms.jipbap.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final RedisPublisher redisPublisher;
    private final RoomService roomService;
    private final MessageService messageService;

    // 대화 & 대화 저장
    @Operation(summary = "메시지 전송, 저장")
    @MessageMapping("/message")
    public void message(MessageDto messageDto) {
        // 클라이언트의 채팅방(topic) 입장, 대화를 위해 리스너와 연동
        roomService.enterMessageRoom(messageDto.getRoomId());

        // Websocket 에 발행된 메시지를 redis 로 발행. 해당 채팅방을 구독한 클라이언트에게 메시지가 실시간 전송됨 (1:N, 1:1 에서 사용 가능)
        redisPublisher.publish(roomService.getTopic(messageDto.getRoomId()), messageDto);

        // DB & Redis 에 대화 저장
        messageService.saveMessage(messageDto);
    }

    // 대화 내역 조회 (최근순 메세지 100개 가지고 오기)
    @Operation(summary = "대화 내역 조회 (최근순 메세지 100개 가지고 오기)")
    @GetMapping("/chat/room/{roomId}/message")
    public CommonResponse<List<MessageDto>> loadMessage(@PathVariable String roomId) {
        return new CommonResponse<>(messageService.loadMessage(roomId));
    }
}