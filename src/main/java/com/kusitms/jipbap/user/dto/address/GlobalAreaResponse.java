package com.kusitms.jipbap.user.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalAreaResponse {
    private Long id;
    private String countryLongName;
    private String stateLongName;
}
