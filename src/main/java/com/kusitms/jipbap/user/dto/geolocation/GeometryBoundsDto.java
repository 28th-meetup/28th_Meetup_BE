package com.kusitms.jipbap.user.dto.geolocation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeometryBoundsDto {
    private GeometryLocationDto northeast;
    private GeometryLocationDto southwest;

}