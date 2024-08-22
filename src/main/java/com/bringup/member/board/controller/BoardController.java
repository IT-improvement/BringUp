package com.bringup.member.board.controller;

import com.bringup.member.board.domain.service.BoardService;
import com.bringup.member.board.dto.request.BoardRequestDto;
import com.bringup.member.board.dto.response.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.property.access.spi.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/posts")
    public List<BoardResponseDto> getPosts(){
        return boardService.getPosts();
    }

    @GetMapping("/createPost")
    public BoardResponseDto createPost(@RequestBody BoardRequestDto boardRequestDto){
        return boardService.createPost(boardRequestDto);
    }

    @GetMapping("/post/{userIndex}")
    public BoardResponseDto getPost(@PathVariable int userIndex){
        return boardService.getPost(userIndex);
    }

    public BoardResponseDto updatePost(@PathVariable int userIndex, @RequestBody BoardRequestDto boardRequestDto) throws Exception{
        return boardService.updatePost(userIndex, boardRequestDto);
    }
}
