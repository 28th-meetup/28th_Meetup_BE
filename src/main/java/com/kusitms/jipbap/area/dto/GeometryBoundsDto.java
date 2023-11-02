package com.kusitms.jipbap.area.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeometryBoundsDto {
    private GeometryLocationDto northeast;
    private GeometryLocationDto southwest;

}
