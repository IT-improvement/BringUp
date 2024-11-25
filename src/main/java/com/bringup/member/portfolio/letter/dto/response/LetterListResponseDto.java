package com.bringup.member.portfolio.letter.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import com.bringup.member.portfolio.letter.domain.LetterEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class LetterListResponseDto extends ResponseDto {

    private String answer1;
    private String answer2;
    private String answer3;

    private LetterListResponseDto(LetterEntity letterEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.answer1=letterEntity.getAnswer1();
        this.answer2=letterEntity.getAnswer2();
        this.answer3=letterEntity.getAnswer3();
    }

    public static ResponseEntity<LetterListResponseDto> success(LetterEntity letterEntity) {
        LetterListResponseDto response = new LetterListResponseDto(letterEntity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<ResponseDto> noExistLetter(){
        ResponseDto response = new ResponseDto(ResponseCode.NOT_EXISTED_LETTER, ResponseMessage.NOT_EXISTED_LETTER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
