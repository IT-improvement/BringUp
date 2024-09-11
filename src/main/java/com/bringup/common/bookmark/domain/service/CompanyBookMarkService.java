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

import static com.bringup.common.enums.MemberErrorCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class CompanyBookMarkService {
    private final CompanyBookMarkRepository companyBookMarkRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CVRepository cvRepository;

    @Transactional
    public CompanyBookMarkResponseDto addCompanyBookMark(CompanyBookMarkRequestDto companyBookMarkRequestDto){
        UserEntity userEntity = userRepository.findByUserIndex(companyBookMarkRequestDto.getUserIndex())
                .orElseThrow(()->new RuntimeException("사용자를 찾을 수 없습니다."));

        Company company = companyRepository.findBycompanyId(companyBookMarkRequestDto.getCompanyIndex())
                .orElseThrow(()->new RuntimeException("기업을 찾을 수 없습니다."));

        Optional<CompanyBookMarkEntity> exitingBookMark = companyBookMarkRepository.findByUserIndexAndCompanyIndex(companyBookMarkRequestDto.getUserIndex(), companyBookMarkRequestDto.getCompanyIndex());
        CompanyBookMarkEntity companyBookMarkEntity;

        if (exitingBookMark.isPresent()){
            companyBookMarkEntity = exitingBookMark.get();
            companyBookMarkEntity.setStatus(BookmarkType.BOOKMARK);
        } else {
            companyBookMarkEntity = new CompanyBookMarkEntity();
            companyBookMarkEntity.setCompanyIndex(companyBookMarkRequestDto.getCompanyIndex());
            companyBookMarkEntity.setUserIndex(companyBookMarkRequestDto.getUserIndex());
            companyBookMarkEntity.setStatus(BookmarkType.BOOKMARK);
        }
        CompanyBookMarkEntity saveBookMark = companyBookMarkRepository.save(companyBookMarkEntity);
        return new CompanyBookMarkResponseDto(saveBookMark);
    }

    public List<CompanyBookMarkResponseDto> getCompanyBookMarks(int userIndex){
        List<CompanyBookMarkEntity> companyBookMarkEntityList = companyBookMarkRepository.findByUserIndexAndStatus(userIndex, "등록");
        return companyBookMarkEntityList.stream()
                .map(CompanyBookMarkResponseDto::new)
                .collect(Collectors.toList());
    }

    public void removeCompanyBookMark(CompanyBookMarkRequestDto companyBookMarkRequestDto){
        CompanyBookMarkEntity companyBookMarkEntity = companyBookMarkRepository.findByUserIndexAndCompanyIndex(companyBookMarkRequestDto.getUserIndex(), companyBookMarkRequestDto.getCompanyIndex())
                .orElseThrow(()->new RuntimeException("해당되는 북마크가 없습니다."));
        companyBookMarkEntity.setStatus(BookmarkType.BOOKMARK); // 그냥 reposiroty 내에서 삭제하는걸로 변경하쇼
        companyBookMarkRepository.save(companyBookMarkEntity);
    }

    public void addCandidate(UserDetailsImpl userDetails, int cvIndex){
        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(()->new BookmarkException(NOT_FOUND_MEMBER_ID));

        CVEntity cv = cvRepository.findByCvIndex(cvIndex);

        CompanyBookMarkEntity companyBookMarkEntity = new CompanyBookMarkEntity();
        companyBookMarkEntity.setCompanyIndex(company.getCompanyId());
        companyBookMarkEntity.setUserIndex(cv.getUserIndex());
        companyBookMarkEntity.setStatus(BookmarkType.VOLUNTEER);
        companyBookMarkRepository.save(companyBookMarkEntity);
    }

    @Transactional
    public List<CandidateResponseDto> candidateList(UserDetailsImpl userDetails){
        int companyIndex = companyRepository.findBycompanyId(userDetails.getId())
                .orElseThrow(() -> new BookmarkException(NOT_FOUND_MEMBER_ID))
                .getCompanyId();

        List<CompanyBookMarkEntity> bookmarks = companyBookMarkRepository.findByCompanyIndexAndStatus(companyIndex, BookmarkType.VOLUNTEER);

        // 북마크된 후보자 리스트를 DTO로 변환
        return bookmarks.stream()
                .map(bookmark -> {
                    CVEntity cv = cvRepository.findByUserIndex(bookmark.getUserIndex())
                            .orElseThrow(() -> new RuntimeException("해당 유저의 이력서를 찾을 수 없습니다."));
                    UserEntity user = userRepository.findById(cv.getUserIndex())
                            .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

                    return new CandidateResponseDto(cv, user);
                })
                .collect(Collectors.toList());
    }
    
}
