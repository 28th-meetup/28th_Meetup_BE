package com.kusitms.jipbap.user.dto.address;

import com.kusitms.jipbap.user.entity.GlobalRegion;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalRegionRequest {
    private String countryLongName;
    private String countryShortName;
    private String countryKorean;
    private String regionName;
    private String regionKorean;

    public GlobalRegion toEntity() {
        return GlobalRegion.builder()
                .countryLongName(countryLongName)
                .countryShortName(countryShortName)
                .countryKorean(countryKorean)
                .regionName(regionName)
                .regionKorean(regionKorean)
                .build();
    }
}
