package com.bringup.common.chat.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chatRoom")
@Entity
public class ChatRoomEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    private String title;
    private Timestamp regDate;
}
