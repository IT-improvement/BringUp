package com.bringup.member.portfolio.certificate.domain;

import com.bringup.member.portfolio.certificate.dto.CertificateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "certificate")
public class CertificateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int certificateIndex;
    private int userIndex;
    private String title;
    private String issueStatus;
    private String issueCenter;
    private String certificateType;


    public CertificateEntity(int userIndex, CertificateRequestDto dto){
        this.userIndex = userIndex;
        this.title = dto.getTitle();
        this.issueStatus = dto.getIssueStatus();
        this.issueCenter = dto.getIssueCenter();
        this.certificateType=dto.getCertificateType();
    }
}
