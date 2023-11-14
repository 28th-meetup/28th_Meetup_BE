package com.kusitms.jipbap.user.entity.area;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_administrative_area")
public class AdministrativeArea {
    @Id
    @Column(name = "administrative_area_id")
    private Long administrativeAreaId;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "global_area_id")
    private GlobalArea globalArea;

    @OneToMany(mappedBy= "administrativeArea")
    private List<LocalArea> localAreaList;

    public AdministrativeArea() {
    }
}