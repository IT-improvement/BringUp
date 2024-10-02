package com.bringup.company.advertisement.repository;

import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PremiumAdvertisementRepository extends JpaRepository<PremiumAdvertisement, Integer> {
    // 매진되지 않고, 현재 날짜가 광고의 기간 내에 있는 광고를 가져오는 메서드
    List<PremiumAdvertisement> findAllByIsSoldOutFalseAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPremiumIdAsc(
            LocalDate startDate, LocalDate endDate);
}
