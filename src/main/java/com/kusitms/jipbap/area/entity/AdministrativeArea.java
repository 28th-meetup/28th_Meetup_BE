package com.kusitms.jipbap.area.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "administrative_area")
public class AdministrativeArea {
    @Id
    @Column(name = "administrative_area_id")
    private Long administrativeAreaId;

    @Column(name = "long_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @OneToMany(mappedBy = "administrativeArea")
    private List<LocalArea> localAreaList;

    public AdministrativeArea() {
    }
}
