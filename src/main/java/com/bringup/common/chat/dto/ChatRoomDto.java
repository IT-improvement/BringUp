package com.bringup.common.chat.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.UUID;

@Data
public class ChatRoomDto {
    private String roomId;  // 채팅방 아이디
    private String roomName;// 채팅방 이름
    private long userCount; // 채팅방 인원수


    private HashMap<String,String> userList = new HashMap<>();

    public ChatRoomDto create(String roomName){
        ChatRoomDto chatRoom = new ChatRoomDto();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;

        return chatRoom;
    }
}
