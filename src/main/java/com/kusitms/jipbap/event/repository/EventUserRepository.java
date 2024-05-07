package com.kusitms.jipbap.event.repository;

import com.kusitms.jipbap.event.model.entity.Event;
import com.kusitms.jipbap.event.model.entity.EventUser;
import com.kusitms.jipbap.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventUserRepository extends JpaRepository<EventUser, Long> {
    boolean existsByUserAndEvent(User user, Event event);
}
