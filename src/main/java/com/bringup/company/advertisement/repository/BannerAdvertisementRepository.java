package com.bringup.company.advertisement.repository;

import com.bringup.company.advertisement.entity.BannerAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerAdvertisementRepository extends JpaRepository<BannerAdvertisement, Integer> {
    List<BannerAdvertisement> findAllByOrderByBannerIdAsc();
}
