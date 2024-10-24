package com.bringup.member.portfolio.career.domain;

import com.bringup.member.portfolio.career.dto.request.CareerInsertRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Career")
public class CareerEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int careerIndex;
    private String careerStart;
    private String careerEnd;
    private String companyName;
    private String careerPosition;
    private int userIndex;
    private String careerDepartment;
    private String careerWork;

    public CareerEntity(CareerInsertRequestDto requestDto) {
        this.careerStart = requestDto.getCareerStart();
        this.careerEnd = requestDto.getCareerEnd();
        this.companyName = requestDto.getCompanyName();
        this.careerPosition = requestDto.getCareerPosition();
        this.careerDepartment = requestDto.getCareerDepartment();
        this.careerWork = requestDto.getCareerWork();
    }
}
