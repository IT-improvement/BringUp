package com.bringup.member.portfolio.school.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.school.dto.SchoolListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public ResponseEntity<? super SchoolListResponseDto> getShcoolList(int userCode){
        List<SchoolEntity> list;
        try{list = schoolRepository.findByUserIndex(userCode);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }

        SchoolEntity highSchool = null;
        List<SchoolEntity> universityList = new ArrayList<>();
        List<SchoolEntity> graduateList = new ArrayList<>();

        for(SchoolEntity school: list){
            String type = school.getType();
            if(type.equals("고등"))
                highSchool = school;
            else if(type.equals("석사")||type.equals("박사"))
                graduateList.add(school);
            else
                universityList.add(school);
        }

        return SchoolListResponseDto.success(highSchool,universityList,graduateList);
    }
}
