package com.kusitms.jipbap.event.aop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
    Page<EventLog> findByCreatedAtBefore(LocalDateTime time, PageRequest pageRequest);
}
