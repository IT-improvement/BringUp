package com.bringup.member.board.domain.entity;

import com.bringup.member.board.dto.request.BoardRequestDto;
import com.bringup.member.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")
public class BoardEntity extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardIndex")
    private int boardIndex;

    @ManyToOne
    @JoinColumn(name = "user_index")
    private UserEntity user;

    private String userEmail;
    private String title;
    private String content;
    private String boardImage;

    // JPA가 사용할 기본 생성자 (no-arg constructor)
    @Builder
    public BoardEntity(int boardIndex, UserEntity user, String userEmail, String title, String content, String boardImage) {
        this.boardIndex = boardIndex;
        this.user = user;
        this.userEmail = userEmail;
        this.title = title;
        this.content = content;
        this.boardImage = boardImage;
    }

    public BoardEntity(BoardRequestDto boardRequestDto){
        this.userEmail = boardRequestDto.getUserEmail();
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.boardImage = boardRequestDto.getBoardImage();
    }
}
