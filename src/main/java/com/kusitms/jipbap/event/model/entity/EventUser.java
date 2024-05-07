package com.kusitms.jipbap.event.model.entity;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_eventuser")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventUser extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Event event;
}
