package com.bringup.member.resume.domain.entity;

import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
    private String status;

    public CVEntity(CVInsertRequestDto dto,String skill, int userIndex){
        this.mainCv = dto.isMainCv();
        this.skill=skill;
        this.title=dto.getTitle();
        this.userIndex=userIndex;
        this.status="생성";
    }

}
