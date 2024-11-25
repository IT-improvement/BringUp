package com.bringup.member.resume.domain.entity.primaryKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CVAwardPK implements Serializable {

    @Column(name = "id")
    private int id;

    @Column(name = "cvIndex")
    private int cvIndex;
}
