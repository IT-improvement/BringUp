package com.bringup.common.bookmark.domain.repository;

import com.bringup.common.bookmark.domain.entity.CompanyBookMarkEntity;
import com.bringup.common.bookmark.dto.response.CandidateResponseDto;
import com.bringup.common.enums.BookmarkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyBookMarkRepository extends JpaRepository<CompanyBookMarkEntity, Integer> {
    List<CompanyBookMarkEntity> findByUserIndexAndStatus(int userIndex, String status);

    Optional<CompanyBookMarkEntity> findByUserIndexAndCompanyIndex(int userIndex, int companyIndex);

    Optional<CompanyBookMarkEntity> findByCompanyIndex(int index);

    List<CandidateResponseDto> findAllByCompanyIndex(int index);

    List<CompanyBookMarkEntity> findByCompanyIndexAndStatus(int companyIndex, BookmarkType bookmarkType);
}
