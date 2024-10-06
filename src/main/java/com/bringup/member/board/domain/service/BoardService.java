package com.bringup.member.board.domain.service;

import com.bringup.common.image.ImageService;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.board.domain.entity.BoardEntity;
import com.bringup.member.board.domain.repository.BoardRepository;
import com.bringup.member.board.dto.request.BoardRequestDto;
import com.bringup.member.board.dto.response.BoardResponseDto;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.exception.MemberException;
import com.bringup.member.board.exception.BoardException;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts(){
        return boardRepository.findAllByOrderByModifiedTimeDesc().stream().map(BoardResponseDto::new).toList();
    }

    @Transactional
    public void createPost(UserDetailsImpl userDetails, BoardRequestDto boardRequestDto, MultipartFile[] boardImage){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));

        BoardEntity board = BoardEntity.builder()
                .user(user)
                .userEmail(user.getUserEmail())
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
    public void updatePost(UserDetailsImpl userDetails, MultipartFile[] boardImage, BoardRequestDto boardRequestDto){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));

        BoardEntity board = boardRepository.findByUser(user)
                .orElseThrow(()->new RuntimeException("작성자가 일치하지 않습니다."));

        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());
        board.setBoardImage(imageService.uploadImages(boardImage));

        boardRepository.save(board);
    }

    @Transactional
    public void deletePost(UserDetailsImpl userDetails, BoardRequestDto boardRequestDto){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(NOT_FOUND_MEMBER_ID));
        BoardEntity board = boardRepository.findByUser(user)
                .orElseThrow(()->new IllegalArgumentException("작성한 유저와 일치하지 않습니다."));

        boardRepository.delete(board);
    }
}
