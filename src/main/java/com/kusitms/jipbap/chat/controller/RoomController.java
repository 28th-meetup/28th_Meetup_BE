package com.kusitms.jipbap.chat.controller;

import com.kusitms.jipbap.chat.model.dto.MessageRequestDto;
import com.kusitms.jipbap.chat.model.dto.MessageResponseDto;
import com.kusitms.jipbap.chat.model.dto.RoomDto;
import com.kusitms.jipbap.chat.service.RoomService;
import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // 채팅방 생성
    @Operation(summary = "신규 채팅방 생성")
    @PostMapping("/room")
    public CommonResponse<MessageResponseDto> createRoom(@RequestBody MessageRequestDto messageRequestDto, @Auth AuthInfo authInfo) {
        return new CommonResponse<>(roomService.createRoom(messageRequestDto, authInfo.getEmail()));
    }

    // 사용자 관련 채팅방 전체 조회
    @Operation(summary = "사용자 속한 채팅방 전체 조회")
    @GetMapping("/rooms")
    public CommonResponse<List<MessageResponseDto>> findAllRoomByUser(@Auth AuthInfo authInfo) {
        return new CommonResponse<>(roomService.findAllRoomByUser(authInfo.getEmail()));
    }

    // 사용자 관련 채팅방 선택 조회
    @Operation(summary = "사용자 속한 채팅방 선택 조회")
    @GetMapping("/room/{roomId}")
    public CommonResponse<RoomDto> findRoom(@PathVariable String roomId, @Auth AuthInfo authInfo) {
        return new CommonResponse<>(roomService.findRoom(roomId, authInfo.getEmail()));
    }

    // 채팅방 삭제
    @Operation(summary = "채팅방 삭제")
    @DeleteMapping("/room/{id}")
    public CommonResponse<String> deleteRoom(@PathVariable Long id, @Auth AuthInfo authInfo) {
        roomService.deleteRoom(id, authInfo.getEmail());
        return new CommonResponse<>("채팅방 삭제 완료");
    }
}