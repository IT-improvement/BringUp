package com.bringup.member.resume.domain.entity;

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
}
