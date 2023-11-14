package com.kusitms.jipbap.user.dto.geolocation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeometryDto {
    private GeometryLocationDto location;
    private String locationType;
}