package com.bringup.member.portfolio.letter.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<LetterEntity,Integer> {
    LetterEntity findByUserIndex(int userIndex);
}
