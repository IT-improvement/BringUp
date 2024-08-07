package com.bringup.member.resume.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "cvProtFolio")
public class CVPortfolioEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cvAndPortfolioIndex;
    private int cvIndex;
    private int portfolioIndex;
    private String status;
}
