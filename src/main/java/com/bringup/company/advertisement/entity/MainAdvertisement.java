package com.bringup.company.advertisement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "main_advertisement")
public class MainAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_index")
    private int mainId;

    @OneToOne
    @JoinColumn(name = "advertisement_index", nullable = false)
    private Advertisement advertisement;

    @Column(name = "exposure_days", nullable = false)
    private int exposureDays;

    @Column(name = "main_image")
    private String main_Image;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // 종료 날짜

    @Column(name = "discount_rate", precision = 5, scale = 2)
    private BigDecimal discountRate;
}
