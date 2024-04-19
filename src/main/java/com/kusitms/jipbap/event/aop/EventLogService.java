package com.kusitms.jipbap.event.aop;

import com.kusitms.jipbap.event.model.entity.Event;
import com.kusitms.jipbap.event.model.response.EnterEventResponse;
import com.kusitms.jipbap.event.model.response.RegisterEventResponse;
import com.kusitms.jipbap.event.repository.EventRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import com.kusitms.jipbap.user.model.entity.User;
import com.kusitms.jipbap.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventLogService {

    private final EventRepository eventRepository;
    private final EventLogRepository eventLogRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createEventRegisterLog(RegisterEventResponse eventInfo, Action action) {
        Event findEvent = eventRepository.findByTitle(eventInfo.getTitle());
        eventLogRepository.save(new EventLog(null, findEvent, null, action));
    }

    @Transactional
    public void createEventEnterLog(EnterEventResponse eventInfo, Action action) {
        Event findEvent = eventRepository.findByTitle(eventInfo.getTitle());
        User findUser = userRepository.findByEmail(eventInfo.getUserEmail()).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다."));
        eventLogRepository.save(new EventLog(null, findEvent, findUser, action));
    }

    @Transactional
    public void createEventErrorLog() {

    }
}
