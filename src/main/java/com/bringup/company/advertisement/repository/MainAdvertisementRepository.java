package com.bringup.company.advertisement.repository;

import com.bringup.company.advertisement.entity.MainAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MainAdvertisementRepository extends JpaRepository<MainAdvertisement, Integer> {
    // 선착순으로 5개의 광고를 가져오는 메서드
    List<MainAdvertisement> findTop6ByOrderByMainIdAsc();

    @Query("SELECT m FROM MainAdvertisement m WHERE (m.startDate <= :startDate AND m.endDate >= :startDate) " +
            "OR (m.startDate <= :endDate AND m.endDate >= :endDate)")
    List<MainAdvertisement> findSoldOutAdsByDateRange(@Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

    // 요청된 날짜 범위 내 예약된 날짜를 조회하는 메서드
    @Query("SELECT ma.startDate FROM MainAdvertisement ma WHERE ma.startDate BETWEEN :startDate AND :endDate")
    List<LocalDate> findReservedDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}