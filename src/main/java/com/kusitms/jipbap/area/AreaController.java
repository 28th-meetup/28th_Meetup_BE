package com.kusitms.jipbap.area;

import com.kusitms.jipbap.area.dto.AddressRequestDto;
import com.kusitms.jipbap.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/area")
public class AreaController {

    private final GeocodingService geocodingService;
    private final AreaService areaService;

    @Operation(summary = "온보딩 시 주소 입력")
    @PostMapping("/address")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<String> saveAddress(@RequestBody AddressRequestDto addressRequestDto) {
        areaService.saveAddress(addressRequestDto);
        return new CommonResponse<>("주소 저장 성공");
    }

    @GetMapping("/get-geo-data")
    public void getGeoData(@RequestParam String address) {
        geocodingService.getGeoDataByAddress(address);
    }
}