package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CVRepository extends JpaRepository<CVEntity, Integer> {
    CVEntity findByCvIndex(int cvIndex);

    List<CVEntity> findAllByUserIndex(int index);
}
