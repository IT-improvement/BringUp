package com.bringup.company.advertisement.entity;

import com.bringup.company.advertisement.enums.Ad_Type;
import com.bringup.company.advertisement.enums.TimeSlot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "premium_advertisement")
public class PremiumAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premium_index")
    private int premium_index;

    @OneToOne
    @JoinColumn(name = "advertisement_index", nullable = false)
    private Advertisement advertisement;

    @Enumerated(EnumType.STRING)
    @Column(name = "ad_type", nullable = false, length = 10)
    private Ad_Type adType; // 광고 타입 (GP, P1, P2, P3)

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "time_slot", nullable = false, length = 50)
    private TimeSlot timeSlot; // 노출 시간대 (예: 01:00 ~ 04:00)

    @Column(name = "premium_image")
    private String image;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // 종료 날짜
}
