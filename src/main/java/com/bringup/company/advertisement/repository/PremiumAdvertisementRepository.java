package com.bringup.company.advertisement.repository;

import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import com.bringup.company.advertisement.enums.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PremiumAdvertisementRepository extends JpaRepository<PremiumAdvertisement, Integer> {
    // 매진되지 않고, 현재 날짜가 광고의 기간 내에 있는 광고를 가져오는 메서드


    @Query("SELECT p FROM PremiumAdvertisement p WHERE p.startDate <= :today AND p.endDate >= :today")
    List<PremiumAdvertisement> findAvailableAds(@Param("today") LocalDate today);
    List<PremiumAdvertisement> findAllByTimeSlotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            TimeSlot timeSlot, LocalDate startDate, LocalDate endDate);
}
