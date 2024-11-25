package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVCertificate;
import com.bringup.member.resume.domain.entity.primaryKey.CVCertificatePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CVCertificateRepository extends JpaRepository<CVCertificate, CVCertificatePK> {
    List<CVCertificate> findByCvIndex(int cvIndex);
}
