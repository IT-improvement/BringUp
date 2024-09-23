package com.bringup.common.bookmark.domain.service;

import com.bringup.common.bookmark.dto.response.CandidateResponseDto;
import com.bringup.common.bookmark.dto.response.CompanyBookMarkResponseDto;
import com.bringup.common.bookmark.exception.BookmarkException;
import com.bringup.common.enums.BookmarkType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.user.entity.Company;
import com.bringup.company.user.repository.CompanyRepository;
import com.bringup.common.bookmark.domain.entity.CompanyBookMarkEntity;
import com.bringup.common.bookmark.domain.repository.CompanyBookMarkRepository;
import com.bringup.common.bookmark.dto.request.CompanyBookMarkRequestDto;
import com.bringup.member.resume.domain.entity.CVEntity;
import com.bringup.member.resume.domain.repository.CVRepository;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_BOOKMARK;
import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class CompanyBookMarkService {
    private final CompanyBookMarkRepository companyBookMarkRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CVRepository cvRepository;

    public void addCompany(UserDetailsImpl userDetails){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new BookmarkException(NOT_FOUND_MEMBER_ID));

        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(()->new BookmarkException(NOT_FOUND_MEMBER_ID));

        CompanyBookMarkEntity companyBookMarkEntity = new CompanyBookMarkEntity();
        companyBookMarkEntity.setUser(user);
        companyBookMarkEntity.setCompany(company);
        companyBookMarkEntity.setStatus(BookmarkType.BOOKMARK);
        companyBookMarkRepository.save(companyBookMarkEntity);
    }

    public List<CompanyBookMarkResponseDto> companyBookMarkList(UserDetailsImpl userDetails){
        UserEntity user = userRepository.findByUserIndex(userDetails.getId())
                .orElseThrow(()->new BookmarkException(NOT_FOUND_MEMBER_ID));

        List<CompanyBookMarkEntity> bookMarkList = companyBookMarkRepository.findByUserAndStatus(user, BookmarkType.BOOKMARK);

        if(bookMarkList.isEmpty()){
            throw new BookmarkException(NOT_FOUND_BOOKMARK);
        }

        return bookMarkList.stream()
                .map(bookMark -> {
                    Company company = companyRepository.findById(bookMark.getCompany().getCompanyId())
                            .orElseThrow(()->new RuntimeException("해당 기업을 찾을 수 없습니다."));
                    return new CompanyBookMarkResponseDto(bookMark);
                })
                .collect(Collectors.toList());
    }

    public void addCandidate(UserDetailsImpl userDetails, int cvIndex){
        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(()->new BookmarkException(NOT_FOUND_MEMBER_ID));

        CVEntity cv = cvRepository.findByCvIndex(cvIndex);

        UserEntity user = userRepository.findById(cv.getUserIndex())
                .orElseThrow(() -> new BookmarkException(NOT_FOUND_MEMBER_ID));

        CompanyBookMarkEntity companyBookMarkEntity = new CompanyBookMarkEntity();
        companyBookMarkEntity.setCompany(company);
        companyBookMarkEntity.setUser(user);
        companyBookMarkEntity.setStatus(BookmarkType.VOLUNTEER);
        companyBookMarkRepository.save(companyBookMarkEntity);
    }

    @Transactional
    public List<CandidateResponseDto> candidateList(UserDetailsImpl userDetails){
        Company company = companyRepository.findBycompanyId(userDetails.getId())
                .orElseThrow(() -> new BookmarkException(NOT_FOUND_MEMBER_ID));

        List<CompanyBookMarkEntity> bookmarks = companyBookMarkRepository.findByCompanyAndStatus(company, BookmarkType.VOLUNTEER);

        // 북마크된 후보자 리스트를 DTO로 변환
        return bookmarks.stream()
                .map(bookmark -> {
                    CVEntity cv = cvRepository.findByUserIndex(bookmark.getUser().getUserIndex())
                            .orElseThrow(() -> new RuntimeException("해당 유저의 이력서를 찾을 수 없습니다."));
                    UserEntity user = userRepository.findById(cv.getUserIndex())
                            .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

                    return new CandidateResponseDto(cv, user);
                })
                .collect(Collectors.toList());
    }
    
}
