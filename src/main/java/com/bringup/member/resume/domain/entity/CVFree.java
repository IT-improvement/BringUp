package com.bringup.member.resume.domain.entity;

import com.bringup.member.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cv_free")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVFree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_index")
    private Long cvIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_index", nullable = false)
    private UserEntity user;  // 프리랜서(유저) 정보와 연결

    @Column(name = "cv_image", nullable = false, length = 255)
    private String cvImage;  // 프로필 이미지

    @Column(name = "resume_title", nullable = false, length = 100)
    private String resumeTitle;

    @Column(name = "career_description", nullable = false, columnDefinition = "TEXT")
    private String careerDescription;

    @Column(name = "skills", nullable = false, length = 255)
    private String skills;

    @Column(name = "portfolio_url", length = 255)
    private String portfolioUrl;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

}