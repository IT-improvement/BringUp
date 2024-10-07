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

import java.time.LocalDateTime;
import java.util.List;

import static com.bringup.common.enums.BoardErrorCode.*;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Transactional
    public void createPost(UserDetailsImpl userDetails, BoardRequestDto boardRequestDto, MultipartFile[] boardImage){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));

        BoardEntity board = BoardEntity.builder()
                .user(user)
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .boardImage(imageService.uploadImages(boardImage))
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public BoardResponseDto getPost(int userIndex){
        return boardRepository.findById(userIndex).map(BoardResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    @Transactional
    public void updatePost(UserDetailsImpl userDetails, MultipartFile[] boardImage, BoardRequestDto boardRequestDto, int boardIndex){
        BoardEntity board = boardRepository.findById(boardIndex)
                .orElseThrow(()->new BoardException(NOT_FOUND_WRITING));
        if (!board.getUser().getUserIndex().equals(userDetails.getId())){
            throw new BoardException(FORBIDDEN_UPDATE_WRITING);
        }

        

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
}
