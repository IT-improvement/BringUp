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

        if(requestDto.getAnswser1()!=null){
            letterEntity.setAnswser1(requestDto.getAnswser1());
        }
        if(requestDto.getAnswser2()!=null) {
            letterEntity.setAnswser2(requestDto.getAnswser2());
        }
        if(requestDto.getAnswser3()!=null) {
            letterEntity.setAnswser3(requestDto.getAnswser3());
        }
        try{
            letterRepository.save(letterEntity);
        }catch (Exception e) {
            return ResponseDto.databaseError();
        }

        return LetterResponseDto.success();

    }
}
