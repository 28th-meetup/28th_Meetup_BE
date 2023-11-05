package com.kusitms.jipbap.chat.domain.entity;

import com.kusitms.jipbap.store.Store;
import com.kusitms.jipbap.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "tb_room")
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // auto-incremential

    @Column(unique = true)
    private String roomId;          // uuid

    private String roomName;

    private String senderName;			// 채팅방 생성자 이름

    private String receiverName;        // 채팅방 수신자 이름

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Message> messageList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)			// 채팅방과 Store는 N:1 연관관계
    @JoinColumn(name = "store_id")
    private Store store;

    // 채팅방 생성
    public Room(Long id, String roomName, String senderName, String roomId, String receiverName, User user, Store store) {
        this.id = id;
        this.roomName = roomName;
        this.senderName = senderName;
        this.roomId = roomId;
        this.receiverName = receiverName;
        this.user = user;
        this.store = store;
    }
}