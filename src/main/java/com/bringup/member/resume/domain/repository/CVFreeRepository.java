package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVFree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVFreeRepository extends JpaRepository<CVFree, Integer> {
}
