package com.kusitms.jipbap.area.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "local_area")
public class LocalArea {
    @Id
    @Column(name = "local_area_id")
    private Long localAreaId;

    @ManyToOne
    @JoinColumn(name = "administrative_area_id")
    private AdministrativeArea administrativeArea;

    @Column(name = "long_name")
    private String longName;

    @OneToMany(mappedBy = "localArea")
    private List<OnBoarding> onBoardingList;

    // Getter and setter methods
}