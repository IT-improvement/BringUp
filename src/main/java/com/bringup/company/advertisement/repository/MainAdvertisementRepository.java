package com.bringup.company.advertisement.repository;

import com.bringup.company.advertisement.entity.MainAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainAdvertisementRepository extends JpaRepository<MainAdvertisement, Integer> {
    // 선착순으로 5개의 광고를 가져오는 메서드
    List<MainAdvertisement> findTop6ByOrderByMainIdAsc();
}
