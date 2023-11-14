package com.kusitms.jipbap.user.repository;

import com.kusitms.jipbap.store.StoreRepositoryExtension;
import com.kusitms.jipbap.user.entity.area.GlobalArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalAreaRepository extends JpaRepository<GlobalArea, Long>, StoreRepositoryExtension {
}
