package com.bringup.member.board.controller;

import com.bringup.common.enums.GlobalErrorCode;
import com.bringup.common.event.exception.ErrorResponseHandler;
import com.bringup.common.response.BfResponse;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.board.domain.entity.BoardEntity;
import com.bringup.member.board.domain.service.BoardService;
import com.bringup.member.board.dto.request.BoardRequestDto;
import com.bringup.member.board.dto.response.BoardResponseDto;
import com.bringup.member.board.exception.BoardException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/notice")
public class BoardController {
    private final BoardService boardService;
    private final ErrorResponseHandler errorResponseHandler;

    @GetMapping("/list")
    public ResponseEntity<BfResponse<?>> getAllPosts(){
        try {
            List<BoardResponseDto> allBoards = boardService.getAllPosts();
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, allBoards));
        }catch (BoardException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/list")
    public ResponseEntity<BfResponse<?>> getPostList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            List<BoardResponseDto> boards = boardService.getPostList(userDetails);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, boards));
        }catch (BoardException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/createPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BfResponse<?>> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestPart("boardRequestDto") BoardRequestDto boardRequestDto, @RequestPart("boardImage") MultipartFile[] boardImage){
        try {
            boardService.createPost(userDetails, boardRequestDto, boardImage);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, "create post successfully"));
        }catch (BoardException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/noticeDetail/{boardIndex}")
    public ResponseEntity<BfResponse<?>> getNoticeDetails(@PathVariable("boardIndex") int boardIndex){
        try {
            BoardResponseDto boardDetails = boardService.getNoticeDetails(boardIndex);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, boardDetails));
        }catch (BoardException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/postDetail/{boardIndex}")
    public ResponseEntity<BfResponse<?>> getPostDetails(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("boardIndex") int boardIndex){
        try {
            BoardResponseDto boardDetails = boardService.getPostDetails(userDetails, boardIndex);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS, boardDetails));
        }catch (BoardException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updatePost/{boardIndex}")
    public ResponseEntity<BfResponse<?>> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardRequestDto boardRequestDto, @RequestParam MultipartFile[] boardImage, @PathVariable("boardIndex") int boardIndex){
        try {
            boardService.updatePost(userDetails, boardRequestDto, boardImage, boardIndex);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS,"update post successfully"));
        }catch (BoardException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deletePost/{boardIndex}")
    public ResponseEntity<BfResponse<?>> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("boardIndex") int boardIndex){
        try {
            boardService.deletePost(userDetails, boardIndex);
            return ResponseEntity.ok(new BfResponse<>(SUCCESS,"delete post successfully"));
        }catch (BoardException e){
            return errorResponseHandler.handleErrorResponse(e.getErrorCode());
        }catch (Exception e){
            return errorResponseHandler.handleErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
