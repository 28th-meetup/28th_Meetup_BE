package com.kusitms.jipbap.event.controller;

import com.kusitms.jipbap.event.model.dto.EventDto;
import com.kusitms.jipbap.event.model.request.EntryEventRequest;
import com.kusitms.jipbap.event.model.request.RegisterEventRequest;
import com.kusitms.jipbap.event.model.response.EnterEventResponse;
import com.kusitms.jipbap.event.model.response.RegisterEventResponse;
import com.kusitms.jipbap.event.service.EventService;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PutMapping
    @Operation(summary = "신규 이벤트 등록 (관리자)")
    public RegisterEventResponse registerNewEvent(@Auth AuthInfo authInfo, @Valid @RequestBody RegisterEventRequest request) {
        EventDto dto = eventService.registerEvent(authInfo.getEmail(), request);
        return new RegisterEventResponse(dto.getId(), dto.getTitle(), dto.getDescription(), dto.getAmount());
    }

    // 이벤트 응모
    @PostMapping
    @Operation(summary = "이벤트 응모 (유저)")
    public EnterEventResponse entryEvent(@Auth AuthInfo authinfo, @Valid @RequestBody EntryEventRequest request) {
        return eventService.entryEvent(authinfo.getEmail(), request.getId());
    }

    // 이벤트 응모 조회
}
