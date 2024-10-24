package com.bringup.member.portfolio.letter.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "letter")
public class LetterEntity {

    @Id
    private int userIndex;

    private String answser1;
    private String answser2;
    private String answser3;
    private String status;
}