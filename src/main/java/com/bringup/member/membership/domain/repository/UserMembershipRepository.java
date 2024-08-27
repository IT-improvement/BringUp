package com.bringup.member.membership.domain.repository;

import com.bringup.member.membership.domain.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Integer> {
    boolean existsByUserIndex(int userIndex);
}