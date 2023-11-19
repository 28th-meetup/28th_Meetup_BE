package com.kusitms.jipbap.store;

import com.kusitms.jipbap.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryExtension {
    Optional<Store> findById(Long id);
    Boolean existsByOwner(User user);
    Optional<Store> findByOwner(User user);


}
