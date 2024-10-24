package com.bringup.member.portfolio.career.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.career.dto.request.CareerInsertRequestDto;
import com.bringup.member.portfolio.career.dto.response.CareerListResponseDto;
import com.bringup.member.portfolio.career.dto.response.CareerResponseDto;
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

    public ResponseEntity<? super CareerResponseDto> insertCareer(int userIndex, CareerInsertRequestDto careerInsertRequestDto){

        String companyName = careerInsertRequestDto.getCompanyName();
        String careerStart= careerInsertRequestDto.getCareerStart();
        String careerEnd = careerInsertRequestDto.getCareerEnd();
        String careerDepartment = careerInsertRequestDto.getCareerDepartment();
        String careerPosition = careerInsertRequestDto.getCareerPosition();

        boolean existCareer = careerRepository.existsByUserIndexAndCompanyNameAndCareerDepartmentAndCareerPositionAndCareerStartAndCareerEnd(userIndex,companyName,careerDepartment,careerPosition,careerStart,careerEnd);

        if(existCareer){
            return CareerResponseDto.existCareer();
        }
        CareerEntity careerEntity = new CareerEntity(userIndex, careerInsertRequestDto);
        try {
            careerRepository.save(careerEntity);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }
        return CareerResponseDto.success();
    }

    public ResponseEntity<? super CareerResponseDto> deleteCareer(int careerIndex){
        try{
            careerRepository.deleteById(careerIndex);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }
        return CareerResponseDto.success();
    }
    public ResponseEntity<? super CareerResponseDto> editCareer(int careerIndex, CareerInsertRequestDto careerInsertRequestDto){
        CareerEntity careerEntity = careerRepository.findByCareerIndex(careerIndex);

        careerEntity.setCareerDepartment(careerInsertRequestDto.getCareerDepartment());
        careerEntity.setCareerPosition(careerInsertRequestDto.getCareerPosition());
        careerEntity.setCareerStart(careerInsertRequestDto.getCareerStart());
        careerEntity.setCareerEnd(careerInsertRequestDto.getCareerEnd());
        careerEntity.setCompanyName(careerInsertRequestDto.getCompanyName());
        careerEntity.setCareerWork(careerInsertRequestDto.getCareerWork());

        try {
            careerRepository.save(careerEntity);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }
        return CareerResponseDto.success();
    }
}
