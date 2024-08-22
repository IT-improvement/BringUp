package com.bringup.member.board.dto.response;

import com.bringup.member.board.domain.entity.BoardEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {
    private int boardIndex;
    private int userIndex;
    private String userEmail;
    private String title;
    private String content;
    private String boardImage;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;

    public BoardResponseDto(BoardEntity boardEntity){
        this.userEmail = boardEntity.getUserEmail();
        this.userIndex = boardEntity.getUserIndex();
        this.boardIndex = boardEntity.getBoardIndex();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.boardImage = boardEntity.getBoardImage();
        this.createTime = boardEntity.getCreatedTime();
        this.modifiedTime = boardEntity.getModifiedTime();
    }
}
