package com.kusitms.jipbap.event.aop;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.event.model.entity.Event;
import com.kusitms.jipbap.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_eventlog")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventLog extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Action action; // REGISTER, ENTER

}
