package com.bringup.member.board.domain.service;

import com.bringup.common.enums.BoardType;
import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.board.domain.entity.BoardEntity;
import com.bringup.member.board.domain.repository.BoardRepository;
import com.bringup.member.board.dto.request.BoardRequestDto;
import com.bringup.member.board.dto.response.BoardResponseDto;
import com.bringup.member.board.exception.BoardException;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.exception.MemberException;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bringup.common.enums.BoardErrorCode.*;
import static com.bringup.common.enums.BoardErrorCode.IMAGE_UPLOAD_FAIL;
import static com.bringup.common.enums.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public List<BoardResponseDto> getAllPosts(){
        List<BoardEntity> boards = boardRepository.findAll();

        if (boards.isEmpty()){
            throw new BoardException(NOT_FOUND_WRITING);
        }

        return boards.stream()
                .map(board -> {
                    return BoardResponseDto.builder()
                            .boardIndex(board.getBoardIndex())
                            .user(board.getUser())
                            .title(board.getTitle())
                            .content(board.getContent())
                            .boardImage(board.getBoardImage())
                            .createPostTime(board.getCreatedPostTime())
                            .updatePostTime(board.getUpdatePostTime())
                            .status(board.getStatus())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BoardResponseDto> getPostList(UserDetailsImpl userDetails){
        List<BoardEntity> boardList = boardRepository.findByUserUserIndex(userDetails.getId());

        if (boardList.isEmpty()){
            throw new BoardException(NOT_FOUND_WRITING);
        }

        return boardList.stream()
                .map(userBoard -> {
                    return BoardResponseDto.builder()
                            .boardIndex(userBoard.getBoardIndex())
                            .user(userBoard.getUser())
                            .title(userBoard.getTitle())
                            .content(userBoard.getContent())
                            .boardImage(userBoard.getBoardImage())
                            .updatePostTime(userBoard.getUpdatePostTime())
                            .status(userBoard.getStatus())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void createPost(UserDetailsImpl userDetails, BoardRequestDto boardRequestDto, MultipartFile[] boardImage){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));

        String createImages;
        try {
            createImages = imageService.uploadImages(boardImage);
        } catch (BoardException e){
            throw new BoardException(IMAGE_UPLOAD_FAIL);
        }

        BoardEntity board = BoardEntity.builder()
                .user(user)
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .boardImage(createImages)
                .status(BoardType.COMPLETE)
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public BoardResponseDto getNoticeDetails(int boardIndex){
        BoardEntity board = boardRepository.findById(boardIndex)
                .orElseThrow(()->new BoardException(NOT_FOUND_WRITING));

        return convertDto(board);
    }

    @Transactional
    public BoardResponseDto getPostDetails(UserDetailsImpl userDetails, int boardIndex){
        BoardEntity board = boardRepository.findById(boardIndex)
                .orElseThrow(()->new BoardException(NOT_FOUND_WRITING));

        if (!board.getUser().getUserIndex().equals(userDetails.getId())){
            throw new BoardException(NOT_FOUND_USER);
        }

        return convertDto(board);
    }

    @Transactional
    public void updatePost(UserDetailsImpl userDetails, BoardRequestDto boardRequestDto, MultipartFile[] boardImage, int boardIndex){
        BoardEntity board = boardRepository.findById(boardIndex)
                .orElseThrow(()->new BoardException(NOT_FOUND_WRITING));

        if (!board.getUser().getUserIndex().equals(userDetails.getId())){
            throw new BoardException(FORBIDDEN_UPDATE_WRITING);
        }

        String updateImages;
        try {
            updateImages = imageService.uploadImages(boardImage);
        }
        catch (BoardException e){
            throw new BoardException(IMAGE_UPLOAD_FAIL);
        }

        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());
        board.setBoardImage(updateImages);

        boardRepository.save(board);
    }

    @Transactional
    public void deletePost(UserDetailsImpl userDetails, int boardIndex){
        BoardEntity board = boardRepository.findById(boardIndex)
                .orElseThrow(()->new BoardException(NOT_FOUND_WRITING));

        if (!board.getUser().getUserIndex().equals(userDetails.getId())){
            throw new BoardException(FORBIDDEN_DELETE_WRITING);
        }

        board.setStatus(BoardType.DELETE);
        boardRepository.save(board);
    }

    private BoardResponseDto convertDto(BoardEntity board){
        return BoardResponseDto.builder()
                .boardIndex(board.getBoardIndex())
                .user(board.getUser())
                .title(board.getTitle())
                .content(board.getContent())
                .boardImage(board.getBoardImage())
                .createPostTime(board.getCreatedPostTime())
                .updatePostTime(board.getUpdatePostTime())
                .build();
    }
}
