package com.kusitms.jipbap.store;

import com.kusitms.jipbap.store.dto.RegisterStoreRequestDto;
import com.kusitms.jipbap.store.dto.SearchStoreResponseDto;
import com.kusitms.jipbap.store.dto.StoreDto;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void registerStore(String email, RegisterStoreRequestDto dto) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        storeRepository.save(
                new Store(null, user, dto.getName(), dto.getDescription(), dto.getKoreanYn(), 0D, 0L, 0L, 0L, dto.getMinOrderAmount(), null)
        );

    }

    @Transactional
    public Slice<SearchStoreResponseDto> searchStoreList(String email, Pageable pageable, String keyword, String standard, String order, Long lastId) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        return storeRepository.searchByKeywordOrderBySort(user, pageable, keyword, standard, order, lastId);
    }


}
