package com.bringup.company.advertisement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "advertisement_announcement")
public class AnnouncementAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_index")
    private int announcementId;

    @OneToOne
    @JoinColumn(name = "advertisement_index", nullable = false)
    private Advertisement advertisement;

}
