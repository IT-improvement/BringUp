package com.bringup.member.proposeCV.repository;

import com.bringup.member.proposeCV.domain.ProposeCVEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposeCVRepository extends JpaRepository<ProposeCVEntity, Integer> {
}
