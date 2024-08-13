package com.bringup.admin.notify.Service;

import com.bringup.admin.notify.DTO.NotificationDto;
import com.bringup.admin.notify.Entity.Notification;
import com.bringup.admin.notify.Repository.NotificationRepository;
import com.bringup.common.enums.NotificationType;
import com.bringup.common.enums.RolesType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // 알림 생성 메서드
    public void createNotification(Long userId, RolesType role, NotificationType type, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setRole(role);
        notification.setType(type);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    // 특정 사용자의 읽지 않은 알림 조회
    public List<NotificationDto> getUnreadNotifications(Long userId, String role) {
        List<Notification> notifications = notificationRepository.findByUserIdAndRoleAndIsReadFalse(userId, RolesType.valueOf(role));
        return notifications.stream()
                .map(notification -> NotificationDto.builder()
                        .id(notification.getId())
                        .userId(notification.getUserId())
                        .role(notification.getRole().name())
                        .type(notification.getType().name())
                        .message(notification.getMessage())
                        .isRead(notification.isRead())
                        .createdAt(notification.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 알림 읽음 처리
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.markAsRead();
        notificationRepository.save(notification);
    }
}