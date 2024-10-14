package com.bringup.company.advertisement.entity;

import com.bringup.company.advertisement.enums.Ad_Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "advertisement_premium")
public class PremiumAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premium_index")
    private int premiumIndex;

    @OneToOne
    @JoinColumn(name = "advertisement_index", nullable = false)
    private Advertisement advertisement;

    @Enumerated(EnumType.STRING)
    @Column(name = "ad_type", nullable = false, length = 10)
    private Ad_Type adType; // 광고 타입 (GP, P1, P2, P3)

    @Setter
    @Column(name = "time_slot", nullable = false, length = 50)
    private String timeSlot; // 노출 시간대 (예: 01:00 ~ 04:00)

    @Column(name = "premium_image")
    private String image;

}
