package com.kusitms.jipbap.user.dto.geolocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddressComponentDto {
    @JsonProperty("long_name")
    private String longName;

    @JsonProperty("short_name")
    private String shortName;

    @JsonProperty("types")
    private List<String> types;
}