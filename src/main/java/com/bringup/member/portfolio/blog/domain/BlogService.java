package com.bringup.member.portfolio.blog.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.blog.dto.request.BlogInsertRequestDto;
import com.bringup.member.portfolio.blog.dto.response.BlogResponseDto;
import com.bringup.member.portfolio.blog.dto.response.BlogReadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public ResponseEntity<? super BlogReadResponseDto> getBLogList(int userIndex){
        List<BlogEntity> list = null;
        try{
            list = blogRepository.findByUserIndex(userIndex);
        } catch (Exception e) {
            return ResponseDto.databaseError();
        }
        if(list == null){
            return BlogReadResponseDto.noExistBlog();
        }
        return BlogReadResponseDto.success(list);
    }

    public ResponseEntity<? super BlogResponseDto> insertBlog(BlogInsertRequestDto blogInsertRequestDto, int userIndex){

        boolean existUrl = blogRepository.existsByUrlAndUserIndex(blogInsertRequestDto.getUrl(), userIndex);
        if(existUrl){
            return BlogResponseDto.existUrl();
        }

        BlogEntity blogEntity = new BlogEntity(blogInsertRequestDto, userIndex);

        try{
            blogRepository.save(blogEntity);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }

        return BlogResponseDto.success();

    }
}
