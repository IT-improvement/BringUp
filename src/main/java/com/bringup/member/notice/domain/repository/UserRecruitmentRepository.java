package com.bringup.member.notice.domain.repository;


import com.bringup.member.notice.domain.entity.UserRecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRecruitmentRepository extends JpaRepository<UserRecruitmentEntity, Integer> {
    List<UserRecruitmentEntity> findByRecruitmentIndexIn(List<Integer> recruitmentIndex);
}
