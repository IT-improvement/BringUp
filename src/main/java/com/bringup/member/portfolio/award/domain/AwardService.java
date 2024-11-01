package com.bringup.member.portfolio.award.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.award.dto.AwardRequestDto;
import com.bringup.member.portfolio.award.dto.AwardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;

    public ResponseEntity<? super AwardResponseDto> insertAward(int userIndex, AwardRequestDto awardRequestDto) {
        String title = awardRequestDto.getTitle();
        String organization = awardRequestDto.getOrganization();
        Date awarDate = awardRequestDto.getAwarDate();

        boolean check = awardRepository.existsByOrganizationAndTitleAndAwarDate(organization, title, awarDate);

        if(check)
            return AwardResponseDto.duplicateAward();

        AwardEntity awardEntity = new AwardEntity(userIndex,awardRequestDto);

        try{
            awardRepository.save(awardEntity);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }
        return AwardResponseDto.success();
    }
}
