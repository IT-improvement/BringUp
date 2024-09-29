package com.bringup.company.advertisement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "banner_advertisement")
public class BannerAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private int bannerId;

    @OneToOne
    @JoinColumn(name = "advertisement_index", nullable = false)
    private Advertisement advertisement;

    @Column(name = "exposure_days", nullable = false)
    private int exposureDays;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "banner_image", nullable = false)
    private String bannerImage;

}

