package com.bringup.member.recruitment.domain.entity;

import com.bringup.member.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "recruitment_visit")
public class RecruitmentVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_index")
    private Integer visitIndex;

    @ManyToOne
    @JoinColumn(name = "user_index", nullable = false)
    private UserEntity user;

    @Column(name = "recruitment_index", nullable = false)
    private Integer recruitmentIndex;

    @Column(name = "visit_date", nullable = false, updatable = false)
    private LocalDateTime visitDate;

    @PrePersist
    protected void onCreate(){
        this.visitDate = LocalDateTime.now();
    }
}
