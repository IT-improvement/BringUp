package com.bringup.member.notice.domain.repository;


import com.bringup.member.notice.domain.entity.UserRecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecruitmentRepository extends JpaRepository<UserRecruitmentEntity, Integer> {
}
