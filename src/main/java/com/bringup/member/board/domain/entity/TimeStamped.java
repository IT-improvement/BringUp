package com.bringup.member.board.domain.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AutoCloseable.class)
public class TimeStamped {
    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;
}
