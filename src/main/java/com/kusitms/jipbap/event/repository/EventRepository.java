package com.kusitms.jipbap.event.repository;

import com.kusitms.jipbap.event.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByTitle(String title);
}
