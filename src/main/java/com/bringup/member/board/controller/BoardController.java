package com.bringup.member.board.controller;

import com.bringup.common.image.ImageService;
import com.bringup.member.board.domain.service.BoardService;
import com.bringup.member.board.dto.request.BoardRequestDto;
import com.bringup.member.board.dto.response.BoardResponseDto;
import com.bringup.member.board.dto.response.SuccessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final ImageService imageService;

    @GetMapping("/posts")
    public List<BoardResponseDto> getPosts(){
        return boardService.getPosts();
    }

    @PostMapping("/createPost")
    public BoardResponseDto createPost(@RequestBody BoardRequestDto boardRequestDto){
        return boardService.createPost(boardRequestDto);
    }

    @GetMapping("/post/{userIndex}")
    public BoardResponseDto getPost(@PathVariable int userIndex){
        return boardService.getPost(userIndex);
    }

    @PutMapping("/post/{userIndex}")
    public BoardResponseDto updatePost(@PathVariable int userIndex, @RequestBody BoardRequestDto boardRequestDto) throws Exception{
        return boardService.updatePost(userIndex, boardRequestDto);
    }

    @DeleteMapping("/post/{userIndex}")
    public SuccessResponseDto deletePost(@PathVariable int userIndex, @RequestBody BoardRequestDto boardRequestDto) throws Exception{
        return boardService.deletePost(userIndex, boardRequestDto);
    }
}
