package com.kusitms.jipbap.user.entity;

import com.kusitms.jipbap.user.entity.area.AdministrativeArea;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "tb_global_region")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "country_long_name")
    private String countryLongName;

    @Column(name = "country_short_name")
    private String countryShortName;

    @Column(name = "country_korean")
    private String countryKorean;

    @Column(name="region_name")
    private String regionName;

    @Column(name="region_korean")
    private String regionKorean;
}
