package com.bringup.member.resume.domain.entity;

import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cv")
@Entity
public class CVEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cvIndex;
    private String cvImage;
    private boolean mainCv;
    private String education;
    private String skill;
    private String userIndex;
    private String status;

    public CVEntity(CVInsertRequestDto dto){
        this.cvImage = dto.getCvimage();
        this.mainCv = dto.isMaincv();
        this.education=dto.getEducation();
        this.skill=dto.getSkill();
        this.userIndex=dto.getUserIndex();
        this.status=dto.getStatus();
    }

}
