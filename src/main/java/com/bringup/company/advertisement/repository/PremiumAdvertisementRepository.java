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
    /*List<PremiumAdvertisement> findAllByEndDateBefore(LocalDate date);  // 마감일이 지난 광고 검색
    List<PremiumAdvertisement> findAllByIsSoldOutTrueOrderByPremiumIdAsc();  // 매진되지 않은 광고 검색
*/
    List<PremiumAdvertisement> findAllByTimeSlotAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            TimeSlot timeSlot, LocalDate startDate, LocalDate endDate);
}
