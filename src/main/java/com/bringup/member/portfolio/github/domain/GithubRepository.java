package com.bringup.member.portfolio.github.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubRepository extends JpaRepository<GithubEntity, Integer> {
}
