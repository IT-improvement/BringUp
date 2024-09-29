package com.bringup.member.board.controller;

import com.bringup.common.enums.GlobalSuccessCode;
import com.bringup.common.image.ImageService;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.board.domain.entity.BoardEntity;
import com.bringup.member.board.domain.service.BoardService;
import com.bringup.member.board.dto.request.BoardRequestDto;
import com.bringup.member.board.dto.response.BoardResponseDto;
import com.bringup.member.board.dto.response.SuccessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/posts")
    public List<BoardResponseDto> getPosts(){
        return boardService.getPosts();
    }

    @PostMapping("/createPost")
    public ResponseEntity<BfResponse<?>> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardRequestDto boardRequestDto, @RequestPart MultipartFile[] boardImage){
        boardService.createPost(userDetails,boardRequestDto,boardImage);
        return ResponseEntity.ok(new BfResponse<>(GlobalSuccessCode.SUCCESS, "create board successfully"));
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
