package com.bringup.member.portfolio.school.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.school.dto.SchoolListResponseDto;
import com.bringup.member.portfolio.school.dto.SchoolRequestDto;
import com.bringup.member.portfolio.school.dto.SchoolResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public ResponseEntity<? super SchoolListResponseDto> getShcoolList(int userCode) {
        List<SchoolEntity> list;
        try {
            list = schoolRepository.findByUserIndex(userCode);
        } catch (Exception e) {
            return ResponseDto.databaseError();
        }

        SchoolEntity highSchool = null;
        List<SchoolEntity> universityList = new ArrayList<>();
        List<SchoolEntity> graduateList = new ArrayList<>();

        for (SchoolEntity school : list) {
            String type = school.getType();
            if (type.equals("고등"))
                highSchool = school;
            else if (type.equals("석사") || type.equals("박사"))
                graduateList.add(school);
            else
                universityList.add(school);
        }

        return SchoolListResponseDto.success(highSchool, universityList, graduateList);
    }

    public ResponseEntity<? super SchoolResponseDto> insertOrEditSchool(int userIndex, SchoolRequestDto schoolRequestDto) {
        String type = schoolRequestDto.getType();
        boolean check = schoolRepository.existsByUserIndexAndType(userIndex, type);
        try {
            if (check) {
                SchoolEntity school = schoolRepository.findByUserIndexAndType(userIndex, type);

                school.setSchoolName(schoolRequestDto.getSchoolName());
                school.setDepartment(schoolRequestDto.getDepartment());
                school.setGrade(schoolRequestDto.getGrade());
                school.setMaxGrade(schoolRequestDto.getMaxGrade());
                school.setMajor(schoolRequestDto.getMajor());
                school.setDouble_major(schoolRequestDto.getDouble_major());
                school.setLocation(schoolRequestDto.getLocation());
                school.setStartDate(schoolRequestDto.getStartDate());
                school.setEndDate(schoolRequestDto.getEndDate());
                school.setStartStatus(schoolRequestDto.getStartStatus());
                school.setEndStatus(schoolRequestDto.getEndStatus());

                schoolRepository.save(school);
            } else {
                SchoolEntity schoolEntity = new SchoolEntity(userIndex, schoolRequestDto);
                schoolRepository.save(schoolEntity);
            }
        } catch (Exception e) {
            return ResponseDto.databaseError();
        }
        return SchoolResponseDto.success();
    }
}
