package com.bringup.company.advertisement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "announcement_advertisement")
public class AnnouncementAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_index")
    private int announcementId;

    @OneToOne
    @JoinColumn(name = "advertisement_index", nullable = false)
    private Advertisement advertisement;

    @Column(name = "duration_months", nullable = false)
    private int durationMonths;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // 종료 날짜

}
