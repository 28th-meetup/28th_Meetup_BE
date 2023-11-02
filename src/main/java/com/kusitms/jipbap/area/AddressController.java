package com.kusitms.jipbap.area;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final GeocodingService geocodingService;

    @GetMapping("/get-geo-data")
    public void getGeoData(@RequestParam String address) {
        geocodingService.getGeoDataByAddress(address);
    }
}