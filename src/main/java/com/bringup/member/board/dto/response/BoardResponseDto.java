package com.bringup.member.board.dto.response;

import com.bringup.common.enums.BoardType;
import com.bringup.member.board.domain.entity.BoardEntity;
import com.bringup.member.user.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
    private int boardIndex;
    private UserEntity user;
    private String title;
    private String content;
    private String boardImage;
    private BoardType status;
    private LocalDateTime createPostTime;
    private LocalDateTime updatePostTime;
}
