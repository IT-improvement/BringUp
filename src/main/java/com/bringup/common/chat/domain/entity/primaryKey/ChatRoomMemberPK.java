package com.bringup.common.chat.domain.entity.primaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMemberPK implements Serializable {

    private int roomId;
    private int userIndex;
}
