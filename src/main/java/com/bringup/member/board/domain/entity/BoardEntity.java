package com.bringup.member.board.domain.entity;

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
    @Column(name = "boardIndex")
    private int boardIndex;

    @ManyToOne
    @JoinColumn(name = "userIndex")
    private UserEntity user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "boardImage")
    private String boardImage;

    @Column(name = "created_at")
    private LocalDateTime createdPostTime;

    @Column(name = "updated_at")
    private LocalDateTime updatePostTime;

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
