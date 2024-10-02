package com.bringup.company.advertisement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "premium_advertisement")
public class PremiumAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premium_index")
    private int premiumId;

    @OneToOne
    @JoinColumn(name = "advertisement_index", nullable = false)
    private Advertisement advertisement;

    @Column(name = "ad_type", nullable = false, length = 10)
    private String adType; // 광고 타입 (GP, P1, P2, P3)


    @Column(name = "time_slot", nullable = false, length = 50)
    private String timeSlot; // 노출 시간대 (예: 01:00 ~ 04:00)

    @Column(name = "is_sold_out", nullable = false)
    private boolean isSoldOut; // 매진 여부

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 시작 날짜 (1일 광고지만 설정 필요)

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // 종료 날짜 (start_date와 동일)

    @Column(name = "premium_image", nullable = false)
    private String premiumImage;



    // getters and setters
}
