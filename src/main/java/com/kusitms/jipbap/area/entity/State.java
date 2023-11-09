package com.kusitms.jipbap.area.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "state")
public class State {
    @Id
    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "long_name")
    private String longName;

    @Column(name = "short_name")
    private String shortName;

    @OneToMany(mappedBy = "state")
    private List<AdministrativeArea> administrativeAreaList;
}
