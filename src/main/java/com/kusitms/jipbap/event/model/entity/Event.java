package com.kusitms.jipbap.event.model.entity;

import com.kusitms.jipbap.common.entity.DateEntity;
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

    @Column
    private String title; // 쿠폰 이름

    @Column
    private String description; // 쿠폰 설명

    @Column
    private Long amount; // 쿠폰 개수

    public Event(String title, String description, Long amount) {
        this.title = title;
        this.description = description;
        this.amount = amount;
    }

    public void decreaseAmount() {
        this.amount--;
    }
}
