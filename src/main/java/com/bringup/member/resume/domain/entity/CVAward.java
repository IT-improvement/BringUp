package com.bringup.member.resume.domain.entity;

import com.bringup.member.resume.domain.entity.primaryKey.CVAwardPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cvAward")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CVAwardPK.class)
public class CVAward {

    @Id
    private int id;
    @Id
    private int cvIndex;
}
