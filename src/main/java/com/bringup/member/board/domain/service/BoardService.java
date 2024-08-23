package com.bringup.member.board.domain.service;

import com.bringup.member.board.domain.entity.BoardEntity;
import com.bringup.member.board.domain.repository.BoardRepository;
import com.bringup.member.board.dto.request.BoardRequestDto;
import com.bringup.member.board.dto.response.BoardResponseDto;
import com.bringup.member.board.dto.response.SuccessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts(){
        return boardRepository.findAllByOrderByModifiedTimeDesc().stream().map(BoardResponseDto::new).toList();
    }

    @Transactional
    public BoardResponseDto createPost(BoardRequestDto boardRequestDto){
        BoardEntity boardEntity = new BoardEntity(boardRequestDto);
        boardRepository.save(boardEntity);
        return new BoardResponseDto(boardEntity);
    }

    @Transactional
    public BoardResponseDto getPost(int userIndex){
        return boardRepository.findById(userIndex).map(BoardResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    @Transactional
    public BoardResponseDto updatePost(int userIndex, BoardRequestDto boardRequestDto) throws Exception{
        BoardEntity boardEntity = boardRepository.findById(userIndex).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!boardRequestDto.getUserEmail().equals(boardEntity.getUserEmail())){
            throw new Exception("이메일이 일치하지 않습니다.");
        }
        boardEntity.updatePost(boardRequestDto);
        return new BoardResponseDto(boardEntity);
    }

    @Transactional
    public SuccessResponseDto deletePost(int userIndex, BoardRequestDto boardRequestDto) throws Exception{
        BoardEntity boardEntity = boardRepository.findById(userIndex).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!boardRequestDto.getUserEmail().equals(boardEntity.getUserEmail())){
            throw new Exception("이메일이 일치하지 않습니다.");
        }
        boardRepository.deleteById(userIndex);
        return new SuccessResponseDto(true);
    }
}
