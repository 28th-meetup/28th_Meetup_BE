package com.kusitms.jipbap.store;

import com.kusitms.jipbap.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreBookmarkRepository extends JpaRepository<StoreBookmark, Long> {
    Boolean existsByUserAndStore(User user, Store store);
}
