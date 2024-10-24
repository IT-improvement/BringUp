package com.bringup.member.portfolio.letter.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.letter.dto.response.LetterListResponseDto;
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
}
