package com.bringup.member.portfolio.portfolio;

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
    private int portfolioindex;
    private String portfolioType;
    private String url;
    private int userIndex;
    private String status;
}
