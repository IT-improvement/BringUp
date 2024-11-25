package com.bringup.member.resume.domain.entity;

import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.user.domain.entity.UserEntity;
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
    private Integer cvIndex;
    private boolean mainCv;
    private String skill;
    private int userIndex;
    private String title;

    public CVEntity(CVInsertRequestDto dto,String skill, int userIndex){
        this.mainCv = dto.isMainCv();
        this.skill=skill;
        this.title=dto.getTitle();
        this.userIndex=userIndex;
    }

}
