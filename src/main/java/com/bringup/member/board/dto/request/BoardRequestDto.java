package com.bringup.member.board.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private String userEmail;
    private String title;
    private String content;
    private String boardImage;
}
