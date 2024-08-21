package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVRepository extends JpaRepository<CVEntity, Integer> {
    CVEntity findByCvIndex(int cvIndex);
}
