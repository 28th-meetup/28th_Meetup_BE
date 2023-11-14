package com.kusitms.jipbap.user.dto.address;

import com.kusitms.jipbap.user.entity.GlobalRegion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalRegionRequest {
    @Schema(description = "국가명(영문)", example = "United States")
    private String countryLongName;

    @Schema(description = "축약 국가명(영문)", example = "US")
    private String countryShortName;

    @Schema(description = "국가명(한글)", example = "미국")
    private String countryKorean;

    @Schema(description = "지역명(영문)", example = "New York")
    private String regionName;

    @Schema(description = "지역명(한글)", example = "뉴욕")
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
