package com.kusitms.jipbap.user.dto.address;

import com.kusitms.jipbap.user.entity.GlobalRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalRegionResponse {
    private Long id;
    private String countryShortName;
    private String countryKorean;
    private String regionName;
    private String regionKorean;

    public GlobalRegionResponse(GlobalRegion globalRegion) {
        this.id = globalRegion.getId();
        this.countryShortName = globalRegion.getCountryShortName();
        this.countryKorean = globalRegion.getCountryKorean();
        this.regionName = globalRegion.getRegionName();
        this.regionKorean = globalRegion.getRegionKorean();
    }
}
