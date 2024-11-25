package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVSchool;
import com.bringup.member.resume.domain.entity.primaryKey.CVSchoolPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CVSchoolRepository extends JpaRepository<CVSchool, CVSchoolPK> {
    List<CVSchool> findByCvIndex(int cvIndex);
}
