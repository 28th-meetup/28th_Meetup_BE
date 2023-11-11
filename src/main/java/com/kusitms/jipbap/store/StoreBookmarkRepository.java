package com.kusitms.jipbap.store;

import com.kusitms.jipbap.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreBookmarkRepository extends JpaRepository<StoreBookmark, Long> {
    Boolean existsByUserAndStore(User user, Store store);
    List<StoreBookmark> findByUser(User user);
}
