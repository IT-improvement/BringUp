package com.bringup.company.advertisement.Entity;


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
    private int advertisementIndex;

    @Column(name = "recruitment_index", nullable = false)
    private int recruitmentIndex;

    @Column(name = "advertisement_image")
    private String advertisementImage;

    @Column(name = "type")
    private String type;

    @Column(name = "displaytime")
    private String displayTime; // 년-월-일 형태로 저장

    @Column(name = "status")
    private String status;
}
