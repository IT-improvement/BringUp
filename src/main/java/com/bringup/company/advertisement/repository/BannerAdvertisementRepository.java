package com.bringup.company.advertisement.repository;

import com.bringup.company.advertisement.entity.BannerAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BannerAdvertisementRepository extends JpaRepository<BannerAdvertisement, Integer> {
    List<BannerAdvertisement> findAllByOrderByBannerIdAsc();

    @Query("SELECT ba FROM BannerAdvertisement ba " +
            "JOIN ba.advertisement a " +
            "WHERE a.status = 'ACTIVE' " +
            "AND a.startDate <= :currentDate " +
            "AND a.endDate >= :currentDate")
    List<BannerAdvertisement> findActiveBannerAdvertisementsByDateRange(@Param("currentDate") LocalDate currentDate);
}
