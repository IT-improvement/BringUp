package com.bringup.member.portfolio.blog.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.blog.dto.BlogResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public ResponseEntity<? super BlogResponseEntity> getBLogList(int userIndex){
        List<BlogEntity> list = null;
        try{
            list = blogRepository.findByUserIndex(userIndex);
        } catch (Exception e) {
            return ResponseDto.databaseError();
        }
        if(list == null){
            return BlogResponseEntity.noExistBlog();
        }
        return BlogResponseEntity.success(list);
    }
}
