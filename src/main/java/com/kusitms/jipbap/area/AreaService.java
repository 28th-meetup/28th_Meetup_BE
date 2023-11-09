package com.kusitms.jipbap.area;

import com.kusitms.jipbap.area.dto.AddressRequestDto;
import com.kusitms.jipbap.area.entity.OnBoarding;
import com.kusitms.jipbap.area.exception.DeviceIdExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AreaService {
    private final OnBoardingRepository onBoardingRepository;

    @Transactional
    public void saveAddress(AddressRequestDto addressRequestDto) {

        // 주소 체계 나눠서 저장
        // getGeoDataByAddress(addressRequestDto.getAddress());

        if(onBoardingRepository.existsByDeviceId(addressRequestDto.getDeviceId())) throw new DeviceIdExistsException("이미 등록된 기기입니다.");
        onBoardingRepository.save(
                OnBoarding.builder()
                        .detailAddress(addressRequestDto.getAddress())
                        .deviceId(addressRequestDto.getDeviceId())
                        .build()
        );

    }
}
