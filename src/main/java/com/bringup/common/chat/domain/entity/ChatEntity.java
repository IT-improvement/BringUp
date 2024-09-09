package com.bringup.common.chat.domain.entity;

import com.bringup.common.utill.DateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class ChatEntity extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatId;
    private int roomId;
    private int senderUserIndex;
    private String message;

}
