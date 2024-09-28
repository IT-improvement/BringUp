package com.bringup.company.advertisement.repository;

import com.bringup.company.advertisement.entity.Advertisement;
import com.bringup.company.advertisement.entity.PremiumAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumAdvertisementRepository extends JpaRepository<PremiumAdvertisement, Integer> {
}
