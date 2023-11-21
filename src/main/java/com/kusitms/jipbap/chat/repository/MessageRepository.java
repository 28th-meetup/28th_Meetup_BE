package com.kusitms.jipbap.chat.repository;

import com.kusitms.jipbap.chat.domain.entity.Message;
import com.kusitms.jipbap.chat.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findTop100ByRoomOrderById(Room room);

    Message findTopByRoomOrderById(Room room);
    Message findTopByRoomOrderBySentTimeDesc(Room room);
}