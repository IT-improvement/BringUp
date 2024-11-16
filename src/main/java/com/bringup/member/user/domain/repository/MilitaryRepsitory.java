package com.bringup.member.user.domain.repository;

import com.bringup.member.user.domain.entity.MilitaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilitaryRepsitory extends JpaRepository<MilitaryEntity, Integer> {
}
