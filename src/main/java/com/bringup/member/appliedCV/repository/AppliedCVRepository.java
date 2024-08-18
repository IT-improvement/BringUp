package com.bringup.member.appliedCV.repository;

import com.bringup.member.appliedCV.domain.AppliedCVEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppliedCVRepository extends JpaRepository<AppliedCVEntity, Integer> {
}
