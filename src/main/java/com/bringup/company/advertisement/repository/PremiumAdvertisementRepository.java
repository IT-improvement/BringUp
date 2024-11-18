package com.bringup.company.advertisement.repository;

import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PremiumAdvertisementRepository extends JpaRepository<PremiumAdvertisement, Integer> {
    // 매진되지 않고, 현재 날짜가 광고의 기간 내에 있는 광고를 가져오는 메서드
    /*List<PremiumAdvertisement> findAllByIsSoldOutFalseAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPremiumIdAsc(
            LocalDate startDate, LocalDate endDate);*/
    /*List<PremiumAdvertisement> findAllByEndDateBefore(LocalDate date);  // 마감일이 지난 광고 검색
    List<PremiumAdvertisement> findAllByIsSoldOutTrueOrderByPremiumIdAsc();  // 매진되지 않은 광고 검색
*/

    @Query("SELECT pa FROM PremiumAdvertisement pa " +
            "JOIN pa.advertisement a " +
            "WHERE a.status = 'ACTIVE' " +
            "AND a.startDate <= :currentDate " +
            "AND a.endDate >= :currentDate " +
            "AND a.display LIKE %:currentDateStr%")
    List<PremiumAdvertisement> findActivePremiumAdvertisementsByDateRangeAndDisplayDate(
            @Param("currentDate") LocalDate currentDate,
            @Param("currentDateStr") String currentDateStr);


    @Query("SELECT pa FROM PremiumAdvertisement pa WHERE pa.advertisement.startDate <= :endDate AND pa.advertisement.endDate >= :startDate")
    List<PremiumAdvertisement> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<PremiumAdvertisement> findByAdvertisement(Advertisement advertisementIndex);


}
