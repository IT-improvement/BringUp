package com.bringup.admin.notify.Controller;

import com.bringup.admin.notify.DTO.NotificationDto;
import com.bringup.admin.notify.Service.NotificationService;
import com.bringup.common.enums.GlobalSuccessCode;
import com.bringup.common.response.BfResponse;
import com.bringup.member.user.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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