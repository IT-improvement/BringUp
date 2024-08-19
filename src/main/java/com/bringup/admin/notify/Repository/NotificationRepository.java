package com.bringup.admin.notify.Repository;


import com.bringup.admin.notify.Entity.Notification;
import com.bringup.common.enums.RolesType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndRoleAndIsReadFalse(Long userId, RolesType role);  // 읽지 않은 알림 조회
}