package com.bringup.common.chat.domain;

import com.bringup.common.chat.dto.ChatRoomDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
//추후 DB와 연결 시 Service 와 Repository 로 분리 예정
public class ChatRepository {

    private Map<String, ChatRoomDto> ChatRoomDtoMap;

    @PostConstruct
    public void init(){
        ChatRoomDtoMap = new HashMap<>();
    }

    // 전체 채팅방 조회
    public List<ChatRoomDto> findAllRoom(){
        //채팅방 생성 순서를 최근순으로 반환
        List ChatRoomDtos = new ArrayList<>(ChatRoomDtoMap.values());
        Collections.reverse(ChatRoomDtos);

        return ChatRoomDtos;
    }

    // roomId 기준으로 채팅방 찾기
    public ChatRoomDto findByRoomId(String roomId){

        return ChatRoomDtoMap.get(roomId);
    }

    // roomName 으로 채팅방 만들기
    public ChatRoomDto createChatRoomDto(String roomName){
        //채팅방 이름으로 채팅 방 생성후
        ChatRoomDto ChatRoomDto = new ChatRoomDto().create(roomName);
        //map에 채팅방 아이디와 만들어진 채팅룸을 저장
        ChatRoomDtoMap.put(ChatRoomDto.getRoomId(), ChatRoomDto);

        return ChatRoomDto;
    }
}
