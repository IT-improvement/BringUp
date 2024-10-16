package com.bringup.company.advertisement.repository;

import com.bringup.common.enums.StatusType;
import com.bringup.company.advertisement.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
    Optional<Advertisement> findByAdvertisementIndex(int advertisementIndex);

    List<Advertisement> findAllByStatusAndStartDate(StatusType status, LocalDate startDate);

    List<Advertisement> findAllByStatusAndEndDate(StatusType status, LocalDate endDate);

}