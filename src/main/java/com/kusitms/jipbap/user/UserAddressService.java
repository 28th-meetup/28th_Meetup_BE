package com.kusitms.jipbap.user;

import com.kusitms.jipbap.user.dto.address.*;
import com.kusitms.jipbap.user.dto.geolocation.AddressComponentDto;
import com.kusitms.jipbap.user.dto.geolocation.GeocodingAddressDto;
import com.kusitms.jipbap.user.dto.geolocation.GeocodingResponseDto;
import com.kusitms.jipbap.user.entity.GlobalRegion;
import com.kusitms.jipbap.user.exception.*;
import com.kusitms.jipbap.user.repository.GlobalRegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final GlobalRegionRepository globalRegionRepository;
    private final UserRepository userRepository;

    @Value("${secret.geocodingApiKey}")
    private String apiKey;

    @Transactional
    public UserAddressResponse saveUserAddress(UserAddressRequest dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        GlobalRegion globalRegion = globalRegionRepository.findById(dto.getGlobalRegionId())
                .orElseThrow(() -> new RegionNotFoundException("지역 정보가 존재하지 않습니다."));

        setUserData(user, globalRegion, dto);

        return new UserAddressResponse(user);
    }

    public GlobalRegionResponse saveGlobalAreaData(GlobalRegionRequest dto) {
        if (globalRegionRepository.existsByRegionName(dto.getRegionName()))
            throw new RegionExistsException("이미 존재하는 지역입니다.");

        GlobalRegion globalRegion = globalRegionRepository.save(dto.toEntity());
        return new GlobalRegionResponse(globalRegion);
    }

    public List<GlobalRegionResponse> getAllGlobalAreaData() {
        List<GlobalRegion> globalRegionList = globalRegionRepository.findAll();

        List<GlobalRegionResponse> globalRegionResponse = globalRegionList.stream()
                .map(GlobalRegionResponse::new)
                .collect(Collectors.toList());

        return globalRegionResponse;
    }

    @Transactional
    public PostalAddressDto getValidPostalCode(String address) {
        return getGeocodingData(address);
    }

    private PostalAddressDto getGeocodingData(String address) {
        try {
            String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            GeocodingResponseDto responseDto = restTemplate.getForObject(apiUrl, GeocodingResponseDto.class);
            //String response = restTemplate.getForObject(apiUrl, String.class);
            log.info("{} 주소에 대한 getGeocodingData API response 결과 : {} ", address, responseDto.getStatus());

            switch (responseDto.getStatus()) {
                case "OK":
                    return findPostalAddress(responseDto.getResults().get(0));
                case "ZERO_RESULTS":
                    throw new GeocodingUnknownAddressException("주소가 존재하지 않습니다.");
                case "OVER_DAILY_LIMIT":
                case "OVER_QUERY_LIMIT":
                case "REQUEST_DENIED":
                    throw new GeocodingConnectionException("API키가 잘못되었거나 결제가 사용 설정 되지 않았습니다.");
                case "INVALID_REQUEST":
                    throw new GeocodingQueryMissingException("쿼리가 누락되었습니다.");
                case "UNKNOWN_ERROR":
                    throw new GeocodingUnknownAddressException("알 수 없는 지오코딩 에러입니다.");
                default:
                    throw new GeocodingConnectionException("서버 에러입니다.");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw e;
        }
    }

    private void setUserData(User user, GlobalRegion globalRegion, UserAddressRequest dto) {
        user.setGlobalRegion(globalRegion);
        user.setAddress(dto.getAddress());
        user.setDetailAddress(dto.getDetailAddress());
        user.setPostalCode(dto.getPostalCode());
        user.setFcmToken(dto.getFcmToken());
    }

    private PostalAddressDto findPostalAddress(GeocodingAddressDto geocodingAddressDto) {
        try {
            String formattedAddress = geocodingAddressDto.getFormattedAddress(); // 실제 데이터베이스에 저장할 주소

            Double lat = geocodingAddressDto.getGeometry().getLocation().getLat();
            Double lng = geocodingAddressDto.getGeometry().getLocation().getLng();

            String countryName = null;
            String postalCode = null;
            for (AddressComponentDto addressComponent : geocodingAddressDto.getAddressComponentList()) {
                List<String> types = addressComponent.getTypes();
                if (types != null && types.contains("country")) {
                    countryName = addressComponent.getLongName();
                }
                if (types != null && types.contains("postal_code")) {
                    postalCode = addressComponent.getLongName();
                }
                else{
                    throw new PostalCodeNotFoundException("우편번호를 찾을 수 없습니다. 주소를 다시 입력해주세요.");
                }
            }
            return new PostalAddressDto(formattedAddress, postalCode);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
