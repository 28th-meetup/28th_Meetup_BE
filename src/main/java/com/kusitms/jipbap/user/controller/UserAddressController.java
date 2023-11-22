package com.kusitms.jipbap.user.controller;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.user.service.UserAddressService;
import com.kusitms.jipbap.user.model.dto.address.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/addresses")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @Operation(summary = "사용자 주소 설정")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<UserAddressResponse> saveUserAddress(@Valid @RequestBody UserAddressRequest dto) {
        return new CommonResponse<>(userAddressService.saveUserAddress(dto));
    }

    @Operation(summary = "유효한 주소인지 확인하고 우편번호 반환하기")
    @GetMapping("/valid")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<PostalAddressDto> getValidPostalCode(@RequestParam String address) {
        return new CommonResponse<>(userAddressService.getValidPostalCode(address));
    }

    @Operation(summary = "지역 코드 데이터 저장하기")
    @PostMapping("/global-area")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<GlobalRegionResponse> saveGlobalAreaData(@Valid @RequestBody GlobalRegionRequest dto) {
        return new CommonResponse<>(userAddressService.saveGlobalAreaData(dto));
    }

    @Operation(summary = "모든 지역 코드 데이터 조회하기")
    @GetMapping("/global-area")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<List<GlobalRegionResponse>> getAllGlobalAreaData() {
        return new CommonResponse<>(userAddressService.getAllGlobalAreaData());
    }

}
