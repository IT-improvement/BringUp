package com.bringup.company.advertisement.entity;


import com.bringup.common.enums.StatusType;
import com.bringup.company.recruitment.entity.Recruitment;
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

    @OneToOne
    @JoinColumn(name = "recruitment_index", nullable = false)
    private Recruitment recruitment;

    @Column(name = "view_count")
    private Integer v_count = 0;

    @Column(name = "click_count")
    private Integer c_count = 0;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status = StatusType.CRT_WAIT;

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
