package com.bringup.member.resume.domain.entity;

import com.bringup.member.resume.domain.entity.primaryKey.CVSchoolPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cvSchool")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CVSchoolPK.class)
public class CVSchool {

    @Id
    private int schoolIndex;
    @Id
    private int cvIndex;
}
