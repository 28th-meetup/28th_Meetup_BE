package com.kusitms.jipbap.user.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalRegionResponse {
    private Long id;
    private String countryShortName;
    private String regionName;
}
