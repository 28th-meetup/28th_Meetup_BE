package com.kusitms.jipbap.store;

import com.kusitms.jipbap.store.dto.RegisterStoreRequestDto;
import com.kusitms.jipbap.store.dto.StoreDetailResponseDto;
import com.kusitms.jipbap.store.dto.StoreDto;
import com.kusitms.jipbap.store.exception.StoreExistsException;
import com.kusitms.jipbap.store.exception.StoreNotExistsException;
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
    private final StoreBookmarkRepository storeBookmarkRepository;
    private final UserRepository userRepository;

    @Transactional
    public StoreDto registerStore(String email, RegisterStoreRequestDto dto) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        if(storeRepository.existsByOwner(user)) {
            throw new StoreExistsException("이미 가게를 생성한 유저입니다.");
        }
        Store store = storeRepository.save(
                new Store(null, user, dto.getName(), dto.getDescription(), dto.getKoreanYn(), 0D, dto.getMinOrderAmount(),null, 0L, 0L,  0L)
        );
        return new StoreDto(store.getId(), store.getName(), store.getDescription(), store.getKoreanYn(), store.getAvgRate(), store.getBookmarkCount(), store.getImage());
    }

    @Transactional
    public Slice<StoreDetailResponseDto> searchStoreList(String email, Pageable pageable, String keyword, String standard, String order, Long lastId) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        return storeRepository.searchByKeywordOrderBySort(user, pageable, keyword, standard, order, lastId);
    }

    @Transactional
    public StoreDetailResponseDto getStoreDetail(String email, Long storeId) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        Store store = storeRepository.findById(storeId).orElseThrow(()-> new StoreNotExistsException("storeId: "+storeId+"에 해당하는 가게가 존재하지 않습니다."));
        return new StoreDetailResponseDto(
                new StoreDto(
                    store.getId(),
                    store.getName(),
                    store.getDescription(),
                    store.getKoreanYn(),
                    store.getAvgRate(),
                    store.getMinOrderAmount(),
                    store.getImage()
                ),
                isStoreBookmarked(user, store)
        );
    }

    private Boolean isStoreBookmarked(User user, Store store){
        return storeBookmarkRepository.existsByUserAndStore(user, store);
    }
}
