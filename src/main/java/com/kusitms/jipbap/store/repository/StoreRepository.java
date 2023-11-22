package com.kusitms.jipbap.store.repository;

import com.kusitms.jipbap.store.model.entity.Store;
import com.kusitms.jipbap.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryExtension {
    Optional<Store> findById(Long id);
    Boolean existsByOwner(User user);
    Optional<Store> findByOwner(User user);


}
