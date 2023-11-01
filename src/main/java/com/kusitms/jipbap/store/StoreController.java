package com.kusitms.jipbap.store;

import com.kusitms.jipbap.common.response.CommonResponse;
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

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @PostMapping
    public CommonResponse<String> registerStore(@Valid @RequestBody RegisterStoreRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new UserNotFoundException("유저 없음"));

        storeRepository.save(
                new Store(null, user, dto.getName(), dto.getDescription(), dto.getKoreanYn(), dto.getAvgRate(), null)
        );

        return new CommonResponse<>("가게 등록 완료");
    }
}
