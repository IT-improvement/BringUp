package com.bringup.admin.notify.controller;

import com.bringup.admin.notify.dto.NotificationDto;
import com.bringup.admin.notify.service.NotificationService;
import com.bringup.common.enums.GlobalSuccessCode;
import com.bringup.common.response.BfResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/unread/{userId}/{role}")
    public ResponseEntity<BfResponse<List<NotificationDto>>> getUnreadNotifications(
            @PathVariable Long userId,
            @PathVariable String role) {
        List<NotificationDto> notifications = notificationService.getUnreadNotifications(userId, role);
        return ResponseEntity.ok(new BfResponse<>(GlobalSuccessCode.SUCCESS, notifications));
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<BfResponse<Void>> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(new BfResponse<>(GlobalSuccessCode.SUCCESS));
    }
}