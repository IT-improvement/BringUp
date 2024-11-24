package com.bringup.member.resume.domain.entity;

import com.bringup.member.resume.domain.entity.primaryKey.CVCareerPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cvCareer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CVCareerPK.class)
public class CVCareer {

    @Id
    private int careerIndex;
    @Id
    private int cvIndex;
}
