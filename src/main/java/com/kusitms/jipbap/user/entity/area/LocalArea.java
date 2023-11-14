package com.kusitms.jipbap.user.entity.area;


import jakarta.persistence.*;


@Entity
@Table(name = "tb_local_area")
public class LocalArea {
    @Id
    @Column(name = "local_area_id")
    private Long localAreaId;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "administrative_area_id")
    private AdministrativeArea administrativeArea;

    public LocalArea() {
    }

}