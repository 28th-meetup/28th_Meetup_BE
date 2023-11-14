package com.kusitms.jipbap.user;

import com.kusitms.jipbap.user.dto.geolocation.AddressComponentDto;
import com.kusitms.jipbap.user.dto.geolocation.GeocodingAddressDto;
import com.kusitms.jipbap.user.dto.geolocation.GeocodingResponseDto;
import com.kusitms.jipbap.user.exception.GeocodingConnectionException;
import com.kusitms.jipbap.user.exception.GeocodingUnknownAdressException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAddressService {
    @Value("${secret.geocodingApiKey}")
    private String apiKey;

    @Transactional
    public void getGeoDataByAddress(String address){
        JSONObject geocodingData = getGeocodingData(address);
    }

    private JSONObject getGeocodingData(String address) {
        try {
            String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            GeocodingResponseDto responseDto = restTemplate.getForObject(apiUrl, GeocodingResponseDto.class);
            //String response = restTemplate.getForObject(apiUrl, String.class);
            log.info("{} 주소에 대한 getGeocodingData API response 결과 : {} ", address, responseDto.getStatus());

            if ("OK".equals(responseDto.getStatus())) {
                saveUserAddress(responseDto.getResults().get(0));
            } else if ("ZERO_RESULTS".equals(responseDto.getStatus())){
                throw new GeocodingUnknownAdressException("주소가 존재하지 않습니다.");
            } else if ("OVER_DAILY_LIMIT".equals(responseDto.getStatus())) {
                throw new GeocodingConnectionException("API키가 잘못되었거나 결제가 사용 설정 되지 않았습니다.");
            } else if ("OVER_QUERY_LIMIT".equals(responseDto.getStatus())) {
                throw new GeocodingConnectionException("할당량이 초과되었습니다.");
            } else if ("REQUEST_DENIED".equals(responseDto.getStatus())) {
                throw new GeocodingConnectionException("요청이 거부되었습니다.");
            } else if ("INVALID_REQUEST".equals(responseDto.getStatus())) {
                throw new GeocodingUnknownAdressException("쿼리가 누락되었습니다.");
            } else if ("UNKNOWN_ERROR".equals(responseDto.getStatus())) {
                throw new GeocodingConnectionException("서버 에러입니다.");
            } else {
                throw new GeocodingConnectionException("그 외의 오류가 발생했습니다.");
            }
            return new JSONObject(responseDto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveUserAddress(GeocodingAddressDto geocodingAddressDto) {
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
                    postalCode= addressComponent.getLongName();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}