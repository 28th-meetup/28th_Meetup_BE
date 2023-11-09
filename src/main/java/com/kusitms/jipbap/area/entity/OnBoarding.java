package com.kusitms.jipbap.area.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "onboarding")
@Getter
@Setter
@Builder
public class OnBoarding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "onboarding_id")
    private Long onboardingId;

    @ManyToOne
    @JoinColumn(name = "local_area_id")
    private LocalArea localArea;

    @Column(name = "device_id", unique = true)
    private String deviceId;

    @Column(name = "detail_address")
    private String detailAddress;

    // Getter and setter methods
}