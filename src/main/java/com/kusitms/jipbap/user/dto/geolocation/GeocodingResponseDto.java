package com.kusitms.jipbap.user.dto.geolocation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeocodingResponseDto {
    private List<GeocodingAddressDto> results;
    private String status;
}