package com.bringup.common.chat.domain.entity.primaryKey;

import com.bringup.common.utill.DateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chatRoom")
@Entity
public class ChatRoomEntity extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    private String title;
}
