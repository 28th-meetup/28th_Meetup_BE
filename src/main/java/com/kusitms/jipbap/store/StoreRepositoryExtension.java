package com.kusitms.jipbap.store;

import com.kusitms.jipbap.store.dto.StoreDetailResponseDto;
import com.kusitms.jipbap.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface StoreRepositoryExtension {

    Slice<StoreDetailResponseDto> searchByKeywordOrderBySort(User user, Pageable pageable, String keyword, String standard, String order, Long lastId);
    List<Store> searchByNameOrderBySort(User user, Pageable pageable, String keyword, String standard, String order);
}
