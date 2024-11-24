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
public class CVSchoolPK implements Serializable {

    @Column(name = "school_index")
    private int schoolIndex;

    @Column(name = "cvIndex")
    private int cvIndex;
}
