package com.bringup.company.advertisement.repository;

import com.bringup.common.enums.StatusType;
import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.user.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
    Optional<Advertisement> findByAdvertisementIndex(int advertisementIndex);

    List<Advertisement> findAllByStatusAndStartDate(StatusType status, LocalDate startDate);

    List<Advertisement> findAllByStatusAndEndDate(StatusType status, LocalDate endDate);

    // 날짜 범위 내의 광고들을 찾는 메서드 정의
    @Query("SELECT a FROM Advertisement a WHERE a.startDate <= :endDate AND a.endDate >= :startDate")
    List<Advertisement> findAllByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<Advertisement> findAllByDisplayContaining(String date);


    List<Advertisement> findAllByRecruitmentCompany(Company company);
}