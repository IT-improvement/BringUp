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

    @ManyToOne(fetch = FetchType.LAZY) // UserRecruitmentEntity와 다대일 관계 설정
    @JoinColumn(name = "recruitment", referencedColumnName = "recruitment_index", nullable = false)
    private UserRecruitmentEntity recruitmentIndex;

    @ManyToOne(fetch = FetchType.LAZY) // UserEntity와 다대일 관계 설정
    @JoinColumn(name = "user", referencedColumnName = "user_index", nullable = false)
    private UserEntity userIndex;

    @Column(name = "status")
    private String status;
}
