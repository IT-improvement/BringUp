package com.bringup.member.portfolio.letter.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.letter.dto.request.LetterInsertRequestDto;
import com.bringup.member.portfolio.letter.dto.response.LetterListResponseDto;
import com.bringup.member.portfolio.letter.dto.response.LetterResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter
@Service
public class LetterService {

    private final LetterRepository letterRepository;
    public ResponseEntity<? super LetterListResponseDto> listLetter(int userIndex){

        LetterEntity letterEntity = null;

        try{
            letterEntity = letterRepository.findByUserIndex(userIndex);
        } catch (Exception e) {
            return ResponseDto.databaseError();
        }

        return LetterListResponseDto.success(letterEntity);
    }


    public ResponseEntity<? super LetterResponseDto> insertLetter(int userIndex, LetterInsertRequestDto requestDto){
        LetterEntity letterEntity = null;

        try{
            letterEntity = letterRepository.findByUserIndex(userIndex);
        } catch (Exception e) {
            return ResponseDto.databaseError();
        }

        if(requestDto.getAnswer1()!=null){
            letterEntity.setAnswer1(requestDto.getAnswer1());

        }
        if(requestDto.getAnswer2()!=null) {
            letterEntity.setAnswer2(requestDto.getAnswer2());
        }
        if(requestDto.getAnswer3()!=null) {
            letterEntity.setAnswer3(requestDto.getAnswer3());
        }



        try{
            letterRepository.save(letterEntity);
        }catch (Exception e) {
            return ResponseDto.databaseError();
        }

        return LetterResponseDto.success();

    }
}