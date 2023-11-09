package com.kusitms.jipbap.store;

import com.kusitms.jipbap.store.dto.SearchStoreResponseDto;
import com.kusitms.jipbap.store.dto.StoreDto;
import com.kusitms.jipbap.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StoreRepositoryExtension {

    Slice<SearchStoreResponseDto> searchByKeywordOrderBySort(User user, Pageable pageable, String keyword, String standard, String order, Long lastId);
}
