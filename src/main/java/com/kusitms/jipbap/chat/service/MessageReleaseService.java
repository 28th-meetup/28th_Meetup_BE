package com.kusitms.jipbap.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.jipbap.chat.model.dto.MessageDto;
import com.kusitms.jipbap.chat.model.entity.Message;
import com.kusitms.jipbap.chat.model.entity.Room;
import com.kusitms.jipbap.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReleaseService {

    private final RedisTemplate<String, MessageDto> redisTemplateMessage;
    private final MessageRepository messageRepository;
    private final ObjectMapper objectMapper;

    @Async
    public void saveMessage(MessageDto messageDto, Room room) {
        // DB 저장
        Message message = messageRepository.save(new Message(messageDto.getSenderName(), room, messageDto.getMessage()));
        message.updateSentTime(message.getCreatedAt().toString());

        // 1. 직렬화
        redisTemplateMessage.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        // 2. redis 저장
        redisTemplateMessage.opsForList().rightPush(messageDto.getRoomId(), messageDto);

        // 3. redistemplate의 expire() 을 이용해서, Key 를 만료시킬 수 있음
        redisTemplateMessage.expire(messageDto.getRoomId(), 60, TimeUnit.MINUTES);
    }
}