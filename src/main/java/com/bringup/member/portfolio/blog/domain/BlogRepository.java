package com.bringup.member.portfolio.blog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity,Integer> {
    List<BlogEntity> findByUserIndex(int userIndex);

    Boolean existsByUrlAndUserIndex(String url, int userIndex);
}
