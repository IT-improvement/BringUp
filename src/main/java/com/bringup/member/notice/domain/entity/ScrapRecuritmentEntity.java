package com.bringup.member.notice.domain.entity;

import com.bringup.member.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recruitment_bookmark")
@Getter
@Setter
public class ScrapRecuritmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_index")
    private int bookmarkIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_index", referencedColumnName = "recruitment_index", nullable = false)
    private UserRecruitmentEntity recruitmentIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_index", referencedColumnName = "user_index", nullable = false)
    private UserEntity userIndex;
}