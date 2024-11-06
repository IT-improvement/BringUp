package com.bringup.member.portfolio.school.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Integer> {

    List<SchoolEntity> findByUserIndex(int userIndex);
    boolean existsByUserIndexAndType(int userIndex, String type);
    SchoolEntity findByUserIndexAndType(int userIndex, String type);
}
