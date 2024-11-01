package com.bringup.member.portfolio.award.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "award")
public class AwardEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userIndex;
    private String title;
    private String organization;
    private Date awarDate;
    private String details;
}
