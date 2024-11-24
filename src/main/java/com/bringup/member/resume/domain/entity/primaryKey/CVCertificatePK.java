package com.bringup.member.resume.domain.entity.primaryKey;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CVCertificatePK implements Serializable {

    @Column(name = "certificate_index")
    private int certificateIndex;

    @Column(name = "cvIndex")
    private int cvIndex;
}
