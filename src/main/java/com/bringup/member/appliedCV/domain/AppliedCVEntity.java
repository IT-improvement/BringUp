package com.bringup.member.appliedCV.domain;

import com.bringup.member.appliedCV.dto.AppliedDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "applyCV")
public class AppliedCVEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applyIndex;
    private int cvIndex;
    private int recruitmentIndex;
    private String status;

    public AppliedCVEntity(AppliedDto dto){
        this.status = dto.getStatus();
    }
}
