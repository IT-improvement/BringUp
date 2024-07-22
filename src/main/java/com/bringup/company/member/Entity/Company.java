package com.bringup.company.member.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "manageremail", nullable = false, length = 255)
    private String managerEmail;

    @Column(name = "companyname", nullable = false, length = 255)
    private String companyName;

    @Column(name = "companypassword", nullable = false, length = 20)
    private String companyPassword;

    @Column(name = "companyscale", nullable = false, length = 30)
    private String companyScale;

    @Column(name = "companyopendate", nullable = true, length = 30)
    private String companyOpenDate;

    @Column(name = "companylicense", nullable = false, length = 11)
    private String companyLicense;

    @Column(name = "companyphonenumber", nullable = true, length = 11)
    private String companyPhoneNumber;

    @Column(name = "companyadress", nullable = false, length = 255)
    private String companyAddress;

    @Column(name = "companycategory", nullable = false, length = 30)
    private String companyCategory;

    @Column(name = "companycontent", nullable = false, columnDefinition = "TEXT")
    private String companyContent;

    @Column(name = "companywelfare", nullable = false, columnDefinition = "TEXT")
    private String companyWelfare;

    @Column(name = "companyvision", nullable = false, columnDefinition = "TEXT")
    private String companyVision;

    @Column(name = "companyhistory", nullable = false, columnDefinition = "TEXT")
    private String companyHistory;

    @Column(name = "mastername", nullable = false, length = 10)
    private String masterName;

    @Column(name = "managername", nullable = false, length = 10)
    private String managerName;

    @Column(name = "managerphonenumber", nullable = false, length = 10)
    private String managerPhoneNumber;

    @Column(name = "companysize", nullable = false)
    private int companySize;

    @Column(name = "companylogo", nullable = true, length = 255)
    private String companyLogo;

    @Column(name = "openCVkey", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int openCVKey;

}
