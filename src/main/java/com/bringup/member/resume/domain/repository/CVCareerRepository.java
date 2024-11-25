package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVCareer;
import com.bringup.member.resume.domain.entity.primaryKey.CVCareerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVCareerRepository extends JpaRepository<CVCareer, CVCareerPK> {
}
