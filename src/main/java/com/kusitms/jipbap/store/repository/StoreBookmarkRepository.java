package com.kusitms.jipbap.store.repository;

import com.kusitms.jipbap.store.model.entity.Store;
import com.kusitms.jipbap.store.model.entity.StoreBookmark;
import com.kusitms.jipbap.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreBookmarkRepository extends JpaRepository<StoreBookmark, Long> {
    Boolean existsByUserAndStore(User user, Store store);
    List<StoreBookmark> findByUser(User user);
}
