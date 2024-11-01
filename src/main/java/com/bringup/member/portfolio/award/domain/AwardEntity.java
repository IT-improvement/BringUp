package com.bringup.member.portfolio.award.domain;

import com.bringup.member.portfolio.award.dto.AwardRequestDto;
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

    public AwardEntity(int userIndex, AwardRequestDto awardRequestDto) {
        this.userIndex = userIndex;
        this.title = awardRequestDto.getTitle();
        this.organization = awardRequestDto.getOrganization();
        this.awarDate = awardRequestDto.getAwarDate();
        this.details = awardRequestDto.getDetails();
    }
}
