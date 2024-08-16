package com.bringup.member.portfolio.notion.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotionRepository extends JpaRepository<NotionEntity,Integer> {
}
