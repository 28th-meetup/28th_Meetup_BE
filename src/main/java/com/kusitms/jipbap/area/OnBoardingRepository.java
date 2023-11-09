package com.kusitms.jipbap.area;

import com.kusitms.jipbap.area.entity.OnBoarding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnBoardingRepository extends JpaRepository<OnBoarding, Long> {
    boolean existsByDeviceId(String deviceId);;
}
