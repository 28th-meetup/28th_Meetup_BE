package com.kusitms.jipbap.store.dto;

import com.kusitms.jipbap.user.CountryPhoneCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStoreRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private Long minOrderAmount;
    @NotBlank
    private String description;

    @NotBlank
    @Schema(description = "국가 전화번호 코드", example = "KOREA")
    private CountryPhoneCode countryPhoneCode;
    @NotBlank
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNum;

    @NotBlank
    private Long globalRegionId;
    @NotBlank
    private String address;
    @NotBlank
    private String detailAddress;

    @NotBlank
    private String deliveryRegion;
    @NotBlank
    private String operationTime;

    @NotBlank
    private Boolean foodChangeYn;

    private Boolean koreanYn;

}
