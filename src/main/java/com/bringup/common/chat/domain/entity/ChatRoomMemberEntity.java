package com.bringup.common.chat.domain.entity;

import com.bringup.common.utill.DateEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chatRoom")
@Entity
public class ChatRoomMemberEntity extends DateEntity {

    @Id
    private int roomId;

    @Id
    private int userIndex;
}
