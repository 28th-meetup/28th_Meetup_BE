package com.kusitms.jipbap.store;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import com.kusitms.jipbap.store.dto.RegisterStoreRequestDto;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public CommonResponse<String> registerStore(@Auth AuthInfo authInfo, @Valid @RequestBody RegisterStoreRequestDto dto) {
        storeService.registerStore(authInfo.getEmail(), dto);
        return new CommonResponse<>("가게 등록 완료");
    }
}
