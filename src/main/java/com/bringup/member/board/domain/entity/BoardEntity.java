package com.bringup.member.board.domain.entity;

import com.bringup.common.enums.BoardType;
import com.bringup.member.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "board")
public class BoardEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_index")
    private Integer boardIndex;

    @ManyToOne
    @JoinColumn(name = "user_index")
    private UserEntity user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "board_image")
    private String boardImage;

    @Column(name = "created_at")
    private LocalDateTime createdPostTime;

    @Column(name = "updated_at")
    private LocalDateTime updatePostTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BoardType status;

    @PrePersist
    public void preCreateTime(){
        this.createdPostTime = LocalDateTime.now();
        this.updatePostTime = this.createdPostTime;
    }

    @PreUpdate
    public void preUpdateTime(){
        this.updatePostTime = LocalDateTime.now();
    }
}
