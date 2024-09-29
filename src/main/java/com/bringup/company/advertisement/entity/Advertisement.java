package com.bringup.company.advertisement.entity;


import com.bringup.common.enums.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advertisement_index")
    private Integer advertisementIndex;

    @Column(name = "recruitment_index", nullable = false)
    private Integer recruitmentIndex;

    @Column(name = "advertisement_image")
    private String advertisementImage;

    @Column(name = "view_count")
    private Integer count = 0;

    @Column(name = "displaytime")
    private String displayTime; // 년-월-일 형태로 저장

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status = StatusType.ACTIVE;

    // 관계 설정 (각각의 광고 유형과 연관됨)
    @OneToOne(mappedBy = "advertisement")
    private PremiumAdvertisement premiumAdvertisement;

    @OneToOne(mappedBy = "advertisement")
    private MainAdvertisement mainAdvertisement;

    @OneToOne(mappedBy = "advertisement")
    private BannerAdvertisement bannerAdvertisement;

    @OneToOne(mappedBy = "advertisement")
    private AnnouncementAdvertisement announcementAdvertisement;
}
