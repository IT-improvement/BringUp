package com.bringup.company.advertisement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "announcement_advertisement")
public class AnnouncementAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private int announcementId;

    @OneToOne
    @JoinColumn(name = "advertisement_index", nullable = false)
    private Advertisement advertisement;

    @Column(name = "duration_months", nullable = false)
    private int durationMonths;

    @Column(name = "price", nullable = false)
    private int price;
}
