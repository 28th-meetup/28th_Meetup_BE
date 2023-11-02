package com.kusitms.jipbap.chat.repository;

import com.kusitms.jipbap.chat.domain.entity.Room;
import com.kusitms.jipbap.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByUserOrReceiverName(User user, String receiverName);

    Room findByIdAndUserOrIdAndReceiverName(Long id1, User user, Long id2, String nickname);

    Room findBySenderNameAndReceiverName(String senderName, String receiverName);

    Room findByRoomIdAndUserOrRoomIdAndReceiverName(String roomId1, User user, String roomId2, String nickname);

    Optional<Room> findByRoomId(String roomId);
}