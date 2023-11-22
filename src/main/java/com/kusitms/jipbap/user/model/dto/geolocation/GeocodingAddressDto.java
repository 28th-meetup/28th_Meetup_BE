package com.kusitms.jipbap.user.model.dto.geolocation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeocodingAddressDto {
    @JsonProperty("address_components")
    private List<AddressComponentDto> addressComponentList;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    private GeometryDto geometry;

    private String placeId;

    @JsonProperty("types")
    private List<String> typeList;
}
