package com.bringup.member.board.domain.entity;

import com.bringup.member.board.dto.request.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "board")
public class BoardEntity extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardIndex")
    private int boardIndex;
    private int userIndex;
    private String userEmail;
    private String title;
    private String content;
    private String boardImage;
    // JPA가 사용할 기본 생성자 (no-arg constructor)
}
