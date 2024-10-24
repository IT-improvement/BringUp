package com.bringup.member.portfolio.letter.dto.response;

import com.bringup.common.response.ResponseCode;
import com.bringup.common.response.ResponseDto;
import com.bringup.common.response.ResponseMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class LetterListResponseDto extends ResponseDto {

    private String answer1;
    private String answer2;
    private String answer3;

    private LetterListResponseDto(String answer1, String answer2, String answer3) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCES);
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public static ResponseEntity<LetterListResponseDto> success(String answer1, String answer2, String answer3) {
        LetterListResponseDto response = new LetterListResponseDto(answer1, answer2, answer3);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<ResponseDto> noExistLetter(){
        ResponseDto response = new ResponseDto(ResponseCode.NOT_EXISTED_LETTER, ResponseMessage.NOT_EXISTED_LETTER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
