package com.kusitms.jipbap.user.model.dto.geolocation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeometryBoundsDto {
    private GeometryLocationDto northeast;
    private GeometryLocationDto southwest;

}