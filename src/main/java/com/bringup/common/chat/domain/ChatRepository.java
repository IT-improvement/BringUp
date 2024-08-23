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


}
