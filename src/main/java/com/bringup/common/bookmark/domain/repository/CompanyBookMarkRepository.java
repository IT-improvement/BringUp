package com.bringup.common.bookmark.domain.repository;

import com.bringup.common.bookmark.domain.entity.CompanyBookMarkEntity;
import com.bringup.common.bookmark.dto.response.CandidateResponseDto;
import com.bringup.common.enums.BookmarkType;
import com.bringup.company.user.entity.Company;
import com.bringup.member.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyBookMarkRepository extends JpaRepository<CompanyBookMarkEntity, Integer> {
    @EntityGraph(attributePaths = "company")
    List<CompanyBookMarkEntity> findByUserAndStatus(UserEntity user, BookmarkType status);

    boolean existsByCompanyAndUser(Company company, UserEntity user);

    Optional<CompanyBookMarkEntity> findByUserAndCompany(UserEntity user, Company company);

    Optional<CompanyBookMarkEntity> findByCompany(Company company);

    //List<CandidateResponseDto> findAllByCompany(Company company);

    boolean existsByCompanyAndUserAndStatus(Company company, UserEntity user, BookmarkType status);

    List<CompanyBookMarkEntity> findByCompanyAndStatus(Company company, BookmarkType bookmarkType);
    
    int countByStatusAndCompany(BookmarkType bookmarkType, Company company);
}
