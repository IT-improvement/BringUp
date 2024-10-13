package com.bringup.company.advertisement.entity;


import com.bringup.common.enums.StatusType;
import com.bringup.company.recruitment.entity.Recruitment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "display_time")
    private String display;

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

    // String을 List로 변환
    public List<String> getStringListAsList() {
        return Arrays.asList(display.split(","));
    }

    // List를 String으로 변환
    public void setStringListFromList(List<String> list) {
        this.display = String.join(",", list);
    }
}
