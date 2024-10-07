package com.bringup.company.advertisement.repository;

import com.bringup.company.advertisement.entity.AnnouncementAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementAdvertisementRepository extends JpaRepository<AnnouncementAdvertisement, Integer> {
}
