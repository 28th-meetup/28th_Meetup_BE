package com.kusitms.jipbap.chat.service;

import com.kusitms.jipbap.auth.exception.InvalidEmailException;
import com.kusitms.jipbap.chat.model.dto.MessageRequestDto;
import com.kusitms.jipbap.chat.model.dto.MessageResponseDto;
import com.kusitms.jipbap.chat.model.dto.RoomDto;
import com.kusitms.jipbap.chat.model.entity.Message;
import com.kusitms.jipbap.chat.model.entity.Room;
import com.kusitms.jipbap.chat.exception.RoomNotExistsException;
import com.kusitms.jipbap.chat.repository.MessageRepository;
import com.kusitms.jipbap.chat.repository.RoomRepository;
import com.kusitms.jipbap.store.model.entity.Store;
import com.kusitms.jipbap.store.repository.StoreRepository;
import com.kusitms.jipbap.store.exception.StoreNotExistsException;
import com.kusitms.jipbap.user.model.entity.User;
import com.kusitms.jipbap.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    // 채팅방(=topic)에 발행되는 메시지 처리하는 리스너
    private final RedisMessageListenerContainer redisMessageListener;

    // 구독(sub) 처리를 위한 서비스
    private final RedisSubscriber redisSubscriber;

    // 1. redis key = 'MESSAGE_ROOM'
    private static final String Message_Rooms = "MESSAGE_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, RoomDto> opsHashMessageRoom;

    // 2. 채팅방의 대화 메시지 발행을 위한 redis topic(채팅방) 정보
    private Map<String, ChannelTopic> topics;

    // 3. redis 의 Hash 데이터에 접근하기 위한 HashOperations, topics 초기화.
    @PostConstruct
    private void init() {
        opsHashMessageRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    // 채팅방 생성
    @Transactional
    public MessageResponseDto createRoom(MessageRequestDto dto, String email) {
        User sender = userRepository.findByEmail(email).orElseThrow(()->new InvalidEmailException("회원정보가 존재하지 않습니다."));
//        User receiver = userRepository.findById(dto.getReceiverId()).orElseThrow(()->new InvalidEmailException("수신 회원정보가 존재하지 않습니다."));
        Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(()->new StoreNotExistsException("가게 정보를 찾을 수 없습니다."));
        User receiver = store.getOwner();

        // 4. 다른 사람들은 들어올 수 없도록 1:1 (구매자:판매자) 채팅방 구성하기
        Room messageRoom = roomRepository.findBySenderNameAndReceiverName(sender.getUsername(), receiver.getUsername());
        // 5. room 생성하기 - (이미 생성된 채팅방이 아닌 경우)
        if (messageRoom == null) {
            RoomDto roomDto = RoomDto.create(receiver.getUsername(), sender);
            opsHashMessageRoom.put(Message_Rooms, roomDto.getRoomId(), roomDto);      // redis hash 에 채팅방 저장해서, 서버간 채팅방 공유 가능
            messageRoom = roomRepository.save(new Room(roomDto.getId(), roomDto.getRoomName(), roomDto.getSenderName(), roomDto.getRoomId(), roomDto.getReceiverName(), sender, store));

            return new MessageResponseDto(messageRoom);
        // 6. 이미 생성된 채팅방인 경우 기존 채팅방 이동
        } else {
            return new MessageResponseDto(messageRoom);
        }
    }

    // 7. 사용자 관련 채팅방 전체 조회 (생성, 수신)
    @Transactional
    public List<MessageResponseDto> findAllRoomByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new InvalidEmailException("회원정보가 존재하지 않습니다."));
        List<Room> messageRooms = roomRepository.findByUserOrReceiverName(user, user.getUsername());      // sender & receiver 모두 해당 채팅방 조회 가능 (1:1 대화)

        List<MessageResponseDto> messageRoomDtos = new ArrayList<>();

        for (Room messageRoom : messageRooms) {
            //  user 가 sender 인 경우 채팅방의 이름이 receiver로 보이도록
            if (user.getUsername().equals(messageRoom.getSenderName())) {
                MessageResponseDto messageRoomDto = new MessageResponseDto(
                        messageRoom.getId(),
                        messageRoom.getReceiverName(),        // roomName
                        messageRoom.getRoomId(),
                        messageRoom.getSenderName(),
                        messageRoom.getReceiverName());

                // 8. 가장 최신 메시지 & 생성 시간 조회
                Message latestMessage = messageRepository.findTopByRoomOrderBySentTimeDesc(messageRoom);
                if (latestMessage != null) {
                    messageRoomDto.setLatestMessageCreatedAt(latestMessage.getSentTime());
                    messageRoomDto.setLatestMessageContent(latestMessage.getMessage());
                }

                messageRoomDtos.add(messageRoomDto);
            // user 가 receiver 인 경우 채팅방의 이름이 sender로 보이도록
            } else {
                MessageResponseDto messageRoomDto = new MessageResponseDto(
                        messageRoom.getId(),
                        messageRoom.getSenderName(),        // roomName
                        messageRoom.getRoomId(),
                        messageRoom.getSenderName(),
                        messageRoom.getReceiverName());

                // 가장 최신 메시지 & 생성 시간 조회
                Message latestMessage = messageRepository.findTopByRoomOrderBySentTimeDesc(messageRoom);
                if (latestMessage != null) {
                    messageRoomDto.setLatestMessageCreatedAt(latestMessage.getSentTime());
                    messageRoomDto.setLatestMessageContent(latestMessage.getMessage());
                }

                messageRoomDtos.add(messageRoomDto);
            }
        }

        return messageRoomDtos;
    }

    // 사용자 관련 채팅방 선택 조회
    @Transactional
    public RoomDto findRoom(String roomId, String email) {
        Room room = roomRepository.findByRoomId(roomId).orElseThrow(
                ()->new RoomNotExistsException("채팅방 정보를 찾을 수 없습니다.")
        );

        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new InvalidEmailException("회원 정보가 존재하지 않습니다.")
        );
        User receiver = userRepository.findByEmail(email).orElseThrow(
                ()->new InvalidEmailException("채팅 수신 회원 정보가 존재하지 않습니다.")
        );
        Store store = storeRepository.findById(room.getStore().getId()).orElseThrow(
                ()->new StoreNotExistsException("가게 정보가 존재하지 않습니다.")
        );

        // 9. sender & receiver 모두 messageRoom 조회 가능
        room = roomRepository.findByRoomIdAndUserOrRoomIdAndReceiverName(roomId, user, roomId, room.getReceiverName());
        if (room == null) {
            throw new IllegalArgumentException("채팅방이 존재하지 않습니다.");
        }

        RoomDto roomDto = new RoomDto(
                room.getId(),
                room.getRoomName(),
                room.getRoomId(),
                room.getSenderName(),
                room.getReceiverName()
        );

        return roomDto;
    }

    // 10. 채팅방 삭제
    @Transactional
    public void deleteRoom(Long id, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new InvalidEmailException("회원정보가 존재하지 않습니다."));

        Room room = roomRepository.findByIdAndUserOrIdAndReceiverName(id, user, id, user.getUsername());

        // sender 가 삭제할 경우
        if (user.getUsername().equals(room.getSenderName())) {
            roomRepository.delete(room);
            opsHashMessageRoom.delete(Message_Rooms, room.getRoomId());
        // receiver 가 삭제할 경우
        } else if (user.getUsername().equals(room.getReceiverName())) {
            room.setReceiverName("Not_Exist_Receiver");
            roomRepository.save(room);
        }

    }

    // 채팅방 입장
    public void enterMessageRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);        // pub/sub 통신을 위해 리스너를 설정. 대화가 가능해진다
            topics.put(roomId, topic);
        }
    }

    // redis 채널에서 채팅방 조회
    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}