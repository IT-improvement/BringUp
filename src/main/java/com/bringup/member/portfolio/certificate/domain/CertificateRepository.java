package com.bringup.member.portfolio.certificate.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, Integer> {
    List<CertificateEntity> findByUserIndex(int userIndex);
}
