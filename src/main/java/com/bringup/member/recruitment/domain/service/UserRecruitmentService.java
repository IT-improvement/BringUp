package com.bringup.member.recruitment.domain.service;

import com.bringup.common.enums.MemberErrorCode;
import com.bringup.common.enums.RecruitmentErrorCode;
import com.bringup.common.enums.RecruitmentType;
import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.company.recruitment.entity.Recruitment;
import com.bringup.company.recruitment.exception.RecruitmentException;
import com.bringup.company.user.entity.Company;
import com.bringup.member.recruitment.domain.entity.RecruitmentVisit;
import com.bringup.member.recruitment.domain.entity.ScrapRecuritmentEntity;
import com.bringup.member.recruitment.domain.repository.RecruitmentVisitRepository;
import com.bringup.member.recruitment.domain.repository.ScrapRecruitmentRepository;
import com.bringup.member.recruitment.dto.response.RecruitmentVisitResponseDto;
import com.bringup.member.recruitment.dto.response.UserRecruitmentDetailDto;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.exception.MemberException;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.recruitment.dto.response.UserRecruitmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.bringup.company.recruitment.repository.RecruitmentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRecruitmentService {

    private final RecruitmentRepository userRecruitmentRepository;
    private final UserRepository userRepository;
    private final ScrapRecruitmentRepository scrapRecruitmentRepository;
    private final RecruitmentVisitRepository recruitmentVisitRepository;

    // 모든 UserRecruitmentEntity를 조회하는 메서드
    public List<UserRecruitmentDto> getAllRecruitments() {
        List<Recruitment> recruitments = userRecruitmentRepository.findAll();  // 모든 공고를 조회
        List<UserRecruitmentDto> dtoList = new ArrayList<>();  // DTO 리스트 생성

        for (Recruitment recruitment : recruitments) {  // 각 엔티티를 순회
            UserRecruitmentDto dto = convertToDto(recruitment);  // 엔티티를 DTO로 변환
            dtoList.add(dto);  // DTO 리스트에 추가
        }
        return dtoList;  // DTO 리스트 반환
    }

    // Top 100 게시글을 viewCount 기준으로 정렬하여 조회하는 메서드
    public List<UserRecruitmentDto> getTopRecruitments() {
        List<Recruitment> recruitments = userRecruitmentRepository.findTop100ByOrderByViewCountDesc(); // 상위 100개 게시글 조회
        List<UserRecruitmentDto> dtoList = new ArrayList<>();

        for (Recruitment recruitment : recruitments) {
            UserRecruitmentDto dto = convertToDto(recruitment);
            dtoList.add(dto);
        }

        return dtoList; // DTO 리스트 반환
    }


    // 북마크 추가
    public void addBookmark(Integer userIndex, Integer recruitmentIndex) {
        UserEntity user = userRepository.findById(Math.toIntExact(userIndex))
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        Recruitment recruitment = userRecruitmentRepository.findById(Math.toIntExact(recruitmentIndex))
                .orElseThrow(() -> new IllegalArgumentException("공고가 존재하지 않습니다."));

        // 중복 여부 확인
        Optional<ScrapRecuritmentEntity> existingBookmark =
                scrapRecruitmentRepository.findByUserIndexAndRecruitmentIndex(user, recruitment);

        if (existingBookmark.isPresent()) {
            throw new IllegalStateException("이미 북마크된 공고입니다.");
        }

        // 새로운 북마크 생성
        ScrapRecuritmentEntity bookmark = new ScrapRecuritmentEntity();
        bookmark.setUserIndex(user);
        bookmark.setRecruitmentIndex(recruitment);
        bookmark.setStatus("ACTIVE"); // 여기에서 status 값 설정


        scrapRecruitmentRepository.save(bookmark);
    }

    // 북마크 삭제
    public void removeBookmark(Integer userIndex, Integer recruitmentIndex) {
        UserEntity user = userRepository.findById(userIndex)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        Recruitment recruitment = userRecruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new IllegalArgumentException("공고가 존재하지 않습니다."));

        ScrapRecuritmentEntity bookmark = scrapRecruitmentRepository.findByUserIndexAndRecruitmentIndex(user, recruitment)
                .orElseThrow(() -> new IllegalStateException("북마크가 존재하지 않습니다."));

        scrapRecruitmentRepository.delete(bookmark);
    }
    // 북마크 상태 확인 로직
    public boolean isBookmarked(Integer userIndex, Integer recruitmentIndex) {
        // UserEntity 가져오기
        UserEntity user = userRepository.findById(userIndex)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        Recruitment recruitment = userRecruitmentRepository.findById(recruitmentIndex)
                .orElseThrow(() -> new IllegalArgumentException("공고가 존재하지 않습니다."));


        // 북마크 상태 확인
        return scrapRecruitmentRepository
                .findByUserIndexAndRecruitmentIndex(user, recruitment)
                .isPresent();
    }


    public UserRecruitmentDetailDto getRecruitmentDetail(int recruitmentId) {
        // 채용 공고 정보 가져오기
        Recruitment recruitment = userRecruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RecruitmentException(RecruitmentErrorCode.NOT_FOUND_RECRUITMENT));

        // 회사 정보 가져오기
        Company company = recruitment.getCompany();
        String[] images = company.getCompanyImg() != null
                ? company.getCompanyImg().replaceAll(" ", "").replaceAll("\n", "").split(",")
                : new String[0];

        // 회사 정보 및 채용 정보를 함께 DTO로 변환
        return UserRecruitmentDetailDto.builder()
                .c_logo(company.getCompanyLogo())
                .c_name(company.getCompanyName())
                .c_img(images)
                .c_intro(company.getCompanyContent())
                .c_welfare(company.getCompanyWelfare())
                .c_address(company.getCompanyAddress())
                .r_title(recruitment.getRecruitmentTitle())
                .r_workdetail(recruitment.getWorkDetail())
                .r_requirement(recruitment.getRequirement())
                .r_hospitality(recruitment.getHospitality())
                .r_career(recruitment.getCareer())
                .r_salary(recruitment.getSalary())
                .r_period(recruitment.getPeriod())
                .build();
    }

    @Transactional
    public void addRecruitmentVisit(UserDetailsImpl userDetails, int recruitmentIndex){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new RecruitmentException(RecruitmentErrorCode.NOT_FOUND_MEMBER_ID));

        Recruitment recruitment = userRecruitmentRepository.findByRecruitmentIndex(recruitmentIndex)
                .orElseThrow(()->new RecruitmentException(RecruitmentErrorCode.NOT_FOUND_RECRUITMENT));

        boolean exists = recruitmentVisitRepository.existsByUserAndRecruitmentIndex(user, recruitment.getRecruitmentIndex());
        if (exists){
            throw new RecruitmentException(RecruitmentErrorCode.DUPLICATED_RECRUITMENT);
        }

        RecruitmentVisit recruitmentVisit = RecruitmentVisit.builder()
                .user(user)
                .recruitmentIndex(recruitment.getRecruitmentIndex())
                .build();

        recruitmentVisitRepository.save(recruitmentVisit);
    }

    public List<RecruitmentVisitResponseDto> getVisitRecruitment(UserDetailsImpl userDetails){
        UserEntity user = userRepository.findById(userDetails.getId())
                .orElseThrow(()->new MemberException(MemberErrorCode.NOT_FOUND_MEMBER_ID));

        List<RecruitmentVisit> visitList = recruitmentVisitRepository.findByUser(user);

        if (visitList.isEmpty()){
            throw new RecruitmentException(RecruitmentErrorCode.NOT_FOUND_VISIT);
        }

        List<RecruitmentVisitResponseDto> visitDto = new ArrayList<>();

        for (RecruitmentVisit recruitmentVisit : visitList) {
            int recruitmentIndex = recruitmentVisit.getRecruitmentIndex();

            // 공고 조회
            Recruitment recruitment = userRecruitmentRepository.findByRecruitmentIndex(recruitmentIndex)
                    .orElseThrow(() -> new RecruitmentException(RecruitmentErrorCode.NOT_FOUND_RECRUITMENT));

            // 회사 이름 가져오기
            String companyName = recruitment.getCompany().getCompanyName();

            // DTO 변환
            RecruitmentVisitResponseDto dto = new RecruitmentVisitResponseDto(
                    recruitmentVisit.getVisitIndex(),
                    recruitmentVisit.getUser(),
                    recruitmentVisit.getRecruitmentIndex(),
                    recruitmentVisit.getVisitDate(),
                    recruitment.getRecruitmentTitle(),
                    companyName
            );

            visitDto.add(dto);
        }

        return visitDto;
    }

    private UserRecruitmentDto convertToDto(Recruitment recruitment) {
        UserRecruitmentDto dto = new UserRecruitmentDto();
        dto.setRecruitmentIndex(recruitment.getRecruitmentIndex());
        dto.setRecruitmentTitle(recruitment.getRecruitmentTitle());
        dto.setRecruitmentType(recruitment.getRecruitmentType().name());
        dto.setCategory(recruitment.getCategory());
        dto.setSkill(recruitment.getSkill());
        dto.setPeriod(recruitment.getPeriod());
        return dto;
    }

    private RecruitmentVisitResponseDto convertToDto(RecruitmentVisit recruitmentVisit){
        RecruitmentVisitResponseDto dto = new RecruitmentVisitResponseDto();
        dto.setVisitIndex(recruitmentVisit.getVisitIndex());
        dto.setUser(recruitmentVisit.getUser());
        dto.setRecruitmentIndex(recruitmentVisit.getRecruitmentIndex());
        dto.setVisitDate(recruitmentVisit.getVisitDate());
        return dto;
    }
}
