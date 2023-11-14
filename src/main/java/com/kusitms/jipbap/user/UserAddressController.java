package com.kusitms.jipbap.user;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.user.dto.address.GlobalRegionRequest;
import com.kusitms.jipbap.user.dto.address.GlobalRegionResponse;
import com.kusitms.jipbap.user.entity.GlobalRegion;
import com.kusitms.jipbap.user.repository.GlobalRegionRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/addresses")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @Operation(summary = "지역 코드 데이터 저장하기")
    @PostMapping("/global-area")
    public CommonResponse<GlobalRegionResponse> saveGlobalAreaData (@Valid @RequestBody GlobalRegionRequest dto) {
        return new CommonResponse<>(userAddressService.saveGlobalAreaData(dto));
    }
    
}
