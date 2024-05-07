package com.kusitms.jipbap.event.service;

import com.kusitms.jipbap.event.exception.AlreadyExistsEventUserException;
import com.kusitms.jipbap.event.exception.EventExhaustException;
import com.kusitms.jipbap.event.exception.EventNotExistsException;
import com.kusitms.jipbap.event.model.entity.Event;
import com.kusitms.jipbap.event.model.entity.EventUser;
import com.kusitms.jipbap.event.model.request.RegisterEventRequest;
import com.kusitms.jipbap.event.model.response.EnterEventResponse;
import com.kusitms.jipbap.event.model.response.RegisterEventResponse;
import com.kusitms.jipbap.event.repository.EventRepository;
import com.kusitms.jipbap.event.repository.EventUserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import com.kusitms.jipbap.user.model.entity.User;
import com.kusitms.jipbap.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventUserRepository eventUserRepository;

    @Transactional
    public RegisterEventResponse registerEvent(String email, RegisterEventRequest dto) {
        User admin = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("해당 이메일을 가진 관리자가 없습니다"));
        Event savedEvent = eventRepository.save(new Event(admin, dto.getTitle(), dto.getDescription(), dto.getAmount()));
        return new RegisterEventResponse(savedEvent.getId(), savedEvent.getTitle(), savedEvent.getDescription(), savedEvent.getAmount());
    }

    @Transactional
    public EnterEventResponse entryEvent(String email, Long eventId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotExistsException("해당 이벤트를 찾을 수 없습니다."));


        // 이미 해당 이벤트를 등록한 유저일 경우
        if(eventUserRepository.existsByUserAndEvent(user, event)) {
            throw new AlreadyExistsEventUserException("해당 이벤트를 이미 등록한 유저입니다");
        }

        // 이벤트 쿠폰이 모두 소진된 경우
        if(event.getAmount()<=0) {
            throw new EventExhaustException("이벤트 쿠폰이 모두 소진되었습니다.");
        }
        event.decreaseAmount();
        EventUser savedEventUser = eventUserRepository.save(new EventUser(null, user, event));

        // N번~1번 쿠폰 순서대로 발급
        return new EnterEventResponse(user.getEmail(), savedEventUser.getEvent().getTitle(), event.getAmount()+1);
    }
}
