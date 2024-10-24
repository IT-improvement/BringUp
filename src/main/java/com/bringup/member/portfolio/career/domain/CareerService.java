package com.bringup.member.portfolio.career.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.career.dto.response.CareerListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;

    public ResponseEntity<? super CareerListResponseDto> getListCareer(int userIndex){
        List<CareerEntity> list = null;

        try{
            list = careerRepository.findByUserIndex(userIndex);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }

        if(list == null){
            return CareerListResponseDto.noExistCarrer();
        }
        return CareerListResponseDto.success(list);
    }
}
