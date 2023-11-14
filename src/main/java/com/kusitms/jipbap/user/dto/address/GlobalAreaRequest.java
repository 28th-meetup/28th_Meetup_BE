package com.kusitms.jipbap.user.dto.address;

import com.kusitms.jipbap.user.entity.area.GlobalArea;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalAreaRequest {
    private String countryLongName;
    private String countryShortName;
    private String CountryKorean;
    private String stateLongName;
    private String stateShortName;
    private String stateKorean;

    public GlobalArea toEntity() {
        return GlobalArea.builder()
                .countryLongName(countryLongName)
                .countryShortName(countryShortName)
                .CountryKorean(CountryKorean)
                .stateLongName(stateLongName)
                .stateShortName(stateShortName)
                .stateKorean(stateKorean)
                .build();
    }
}
