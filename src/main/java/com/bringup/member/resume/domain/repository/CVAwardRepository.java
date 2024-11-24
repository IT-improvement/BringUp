package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVAward;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.entity.primaryKey.CVAwardPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CVAwardRepository extends JpaRepository<CVAward, CVAwardPK> {
}
