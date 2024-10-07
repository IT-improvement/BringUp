package com.bringup.member.board.dto.response;

import com.bringup.member.board.domain.entity.BoardEntity;
import com.bringup.member.user.domain.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {
    private int boardIndex;
    private UserEntity user;
    private String userEmail;
    private String title;
    private String content;
    private String boardImage;
    private LocalDateTime createPostTime;
    private LocalDateTime updatePostTime;

    public BoardResponseDto(BoardEntity boardEntity){
        this.user = boardEntity.getUser();
        this.userEmail = boardEntity.getUserEmail();
        this.boardIndex = boardEntity.getBoardIndex();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.boardImage = boardEntity.getBoardImage();
    }
}
