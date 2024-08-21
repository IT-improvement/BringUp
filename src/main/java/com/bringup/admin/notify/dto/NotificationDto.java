package com.bringup.admin.notify.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationDto {
    private Long id;
    private int userId;
    private String role;
    private String type;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
