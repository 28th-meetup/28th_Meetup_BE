package com.kusitms.jipbap.user;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import com.kusitms.jipbap.user.dto.address.GlobalAreaRequest;
import com.kusitms.jipbap.user.dto.address.GlobalAreaResponse;
import com.kusitms.jipbap.user.entity.area.GlobalArea;
import com.kusitms.jipbap.user.repository.GlobalAreaRepository;
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
public class AddressController {

    private final GlobalAreaRepository globalAreaRepository;
    @Operation(summary = "국가/주립 코드 데이터 저장")
    @PostMapping("/global-area")
    public CommonResponse<GlobalAreaResponse> saveGloablAreaData (@Valid @RequestBody GlobalAreaRequest dto) {
        GlobalArea globalArea = globalAreaRepository.save(dto.toEntity());
        return new CommonResponse<>(new GlobalAreaResponse(globalArea.getId(), globalArea.getCountryLongName(), globalArea.getStateLongName()));
    }
}
