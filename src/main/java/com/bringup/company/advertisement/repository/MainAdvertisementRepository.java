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

    @Query("SELECT COUNT(ma) FROM MainAdvertisement ma WHERE :date BETWEEN ma.advertisement.startDate AND ma.advertisement.endDate")
    int countAdsByDate(@Param("date") LocalDate date);

}