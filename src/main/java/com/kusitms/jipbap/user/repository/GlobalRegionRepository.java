package com.kusitms.jipbap.user.repository;

import com.kusitms.jipbap.user.model.entity.GlobalRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalRegionRepository extends JpaRepository<GlobalRegion, Long> {
    boolean existsByRegionName(String regionName);
}
