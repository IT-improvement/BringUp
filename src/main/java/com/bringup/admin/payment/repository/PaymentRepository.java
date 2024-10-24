package com.bringup.admin.payment.repository;

import com.bringup.admin.payment.entity.Payment;
import com.bringup.common.enums.RolesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByOrderIndex(Integer orderIdx);

    List<Payment> findByUserIdxAndRoles(Integer userIdx, RolesType roles);
}
