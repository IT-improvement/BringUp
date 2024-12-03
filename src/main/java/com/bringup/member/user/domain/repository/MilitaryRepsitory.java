package com.bringup.member.user.domain.repository;

import com.bringup.member.user.domain.entity.MilitaryEntity;
import com.bringup.member.user.dto.MilitaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilitaryRepsitory extends JpaRepository<MilitaryEntity, Integer> {
    Optional<MilitaryEntity> findByUserIndex(Integer userIndex);


}
