package com.bringup.member.resume.domain.entity;

import com.bringup.member.resume.domain.entity.primaryKey.CVCertificatePK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cvCertificate")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CVCertificatePK.class)
public class CVCertificate {

    @Id
    private int certificateIndex;
    @Id
    private int cvIndex;
}
