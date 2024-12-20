package com.bringup.company.user.entity;

import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_index")
    private Integer companyId;

    @Column(name = "manager_email", nullable = false)
    private String managerEmail;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_password", nullable = false)
    private String companyPassword;

    @Column(name = "company_scale", nullable = false)
    private String companyScale;

    @Column(name = "company_opendate")
    private String companyOpendate;

    @Column(name = "company_license", nullable = false)
    private String companyLicense;

    @Column(name = "company_phonenumber")
    private String companyPhonenumber;

    @Column(name = "company_address", nullable = false)
    private String companyAddress;

    @Column(name = "company_category", nullable = false)
    private String companyCategory;

    @Column(name = "company_content", nullable = false)
    private String companyContent;

    @Column(name = "company_welfare", nullable = false)
    private String companyWelfare;

    @Column(name = "company_vision", nullable = false)
    private String companyVision;

    @Column(name = "company_history", nullable = false)
    private String companyHistory;

    @Column(name = "master_name", nullable = false)
    private String masterName;

    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @Column(name = "manager_phonenumber", nullable = false)
    private String managerPhonenumber;

    @Column(name = "company_size", nullable = false)
    private int companySize;

    @Column(name = "company_logo")
    private String companyLogo;

    @Column(name = "company_img")
    private String companyImg;

    @Column(name = "company_homepage")
    private String companyHomepage;

    @Column(name = "company_subsidiary")
    private String companySubsidiary;

    @Column(name = "company_financial_statements")
    private String companyFinancialStatements;

    @Column(name = "opencv_key", nullable = false, columnDefinition = "int default 0")
    private int opencvKey;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status = StatusType.ACTIVE;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private RolesType role = RolesType.ROLE_COMPANY;

    public void setLogo(String url) {
        this.companyLogo = url;
    }

    public void updateCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void updateManagerPhoneNumber(String managerPhonenumber) {
        this.managerPhonenumber = managerPhonenumber;
    }

    public void inactiveMember() {
        this.status = StatusType.INACTIVE;
    }

    public void resetPassword(String password) {
        this.companyPassword = password;
    }
}