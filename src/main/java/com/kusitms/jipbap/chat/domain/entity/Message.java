package com.kusitms.jipbap.chat.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kusitms.jipbap.common.entity.DateEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "tb_message")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderName;

    private String receiverName;

    private String message;

    private String sentTime;

    // 1.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    // 대화 저장
    public Message(String senderName, Room room, String message, String sentTime) {
        this.senderName = senderName;
        this.room = room;
        this.message = message;
        this.sentTime = sentTime;
    }
}