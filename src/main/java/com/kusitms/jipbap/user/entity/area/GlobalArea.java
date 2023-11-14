package com.kusitms.jipbap.user.entity.area;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "tb_global_area")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "country_long_name")
    private String countryLongName;

    @Column(name = "country_short_name")
    private String countryShortName;

    @Column(name = "country_korean")
    private String CountryKorean;

    @Column(name="state_long_name")
    private String stateLongName;

    @Column(name="state_short_name")
    private String stateShortName;

    @Column(name="state_korean")
    private String stateKorean;

    @OneToMany(mappedBy = "globalArea")
    private List<AdministrativeArea> administrativeAreaList;
}
