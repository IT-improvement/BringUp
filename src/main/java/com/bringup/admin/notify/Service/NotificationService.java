package com.bringup.admin.notify.Service;

import com.bringup.admin.notify.DTO.NotificationDto;
import com.bringup.admin.notify.Entity.Notification;
import com.bringup.admin.notify.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationDto createNotification(Long userId, String role, String type, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setRole(role);
        notification.setType(type);
        notification.setMessage(message);
        Notification savedNotification = notificationRepository.save(notification);
        return toDto(savedNotification);
    }

    public List<NotificationDto> getUnreadNotifications(Long userId, String role) {
        return notificationRepository.findByUserIdAndRoleAndIsReadFalse(userId, role).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    private NotificationDto toDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .role(notification.getRole())
                .type(notification.getType())
                .message(notification.getMessage())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}