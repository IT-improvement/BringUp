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

    // 채팅방 인원 +1
    public void increaseUser(String roomId){

        ChatRoomDto ChatRoomDto = ChatRoomDtoMap.get(roomId);
        ChatRoomDto.setUserCount(ChatRoomDto.getUserCount()+1);
    }

    // 채팅방 인원 -1
    public void decreaseUser(String roomId){

        ChatRoomDto ChatRoomDto = ChatRoomDtoMap.get(roomId);
        ChatRoomDto.setUserCount(ChatRoomDto.getUserCount()-1);
    }

    //채팅방 유저 리스트에 유저추가
    public  String addUser(String roomId, String userName){

        ChatRoomDto ChatRoomDto = ChatRoomDtoMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();
        //아이디 중복 확인 후 userList에 추가
        ChatRoomDto.getUserList().put(userUUID,userName);

        return userUUID;
    }
    // 채팅방 유저 이름 중복 확인
    public String isDuplicateName(String roomId,String username){

        ChatRoomDto ChatRoomDto = ChatRoomDtoMap.get(roomId);
        String temp = username;

        // 만약 username이 중복이라면 랜덤한 숫자를 붙여준다.
        // 이 때 랜덤한 숫자를 붙였을때 getUserList 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기
        while(ChatRoomDto.getUserList().containsValue(temp)){
            int ranNum = (int) (Math.random() * 100) + 1;
            temp = username+ranNum;
        }

        return temp;
    }
    // 채팅방 유저 리스트 삭제
    public void deleteUser(String roomId,String userUUID){
        ChatRoomDto ChatRoomDto = ChatRoomDtoMap.get(roomId);
        ChatRoomDto.getUserList().remove(userUUID);
    }

    // 채팅방 userName 조회
    public String getUserName(String roomId,String userUUID){
        ChatRoomDto ChatRoomDto = ChatRoomDtoMap.get(roomId);

        return ChatRoomDto.getUserList().get(userUUID);
    }
}
