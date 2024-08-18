package com.bringup.member.portfolio.portfolio.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolio")
@Entity
public class PortfolioEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int portfolioIndex;
    private String portfolioType;
    private int userIndex;
    private String status;
}
