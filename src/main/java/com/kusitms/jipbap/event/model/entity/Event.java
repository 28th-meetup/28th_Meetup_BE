package com.kusitms.jipbap.event.model.entity;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_event")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Event extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 고유 pk

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin; // 쿠폰 생성 admin

    @Column(unique = true)
    private String title; // 쿠폰 이름

    @Column
    private String description; // 쿠폰 설명

    @Column
    private Long amount; // 쿠폰 개수

    public Event(User admin, String title, String description, Long amount) {
        this.admin = admin;
        this.title = title;
        this.description = description;
        this.amount = amount;
    }

    public void decreaseAmount() {
        this.amount--;
    }
}
