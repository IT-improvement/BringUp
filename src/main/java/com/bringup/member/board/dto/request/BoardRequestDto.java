package com.bringup.member.board.dto.request;

import com.bringup.member.user.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private int boardIndex;
    private UserEntity user;
    private String title;
    private String content;
    private String boardImage;
    private LocalDateTime createdPostTime;
    private LocalDateTime updatePostTime;
}
