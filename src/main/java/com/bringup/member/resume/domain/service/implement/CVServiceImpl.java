package com.bringup.member.resume.domain.service.implement;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.award.domain.AwardEntity;
import com.bringup.member.portfolio.award.domain.AwardRepository;
import com.bringup.member.portfolio.blog.domain.BlogEntity;
import com.bringup.member.portfolio.blog.domain.BlogRepository;
import com.bringup.member.portfolio.career.domain.CareerEntity;
import com.bringup.member.portfolio.career.domain.CareerRepository;
import com.bringup.member.portfolio.certificate.domain.CertificateEntity;
import com.bringup.member.portfolio.certificate.domain.CertificateRepository;
import com.bringup.member.portfolio.github.domain.GithubEntity;
import com.bringup.member.portfolio.github.domain.GithubRepository;
import com.bringup.member.portfolio.school.domain.SchoolEntity;
import com.bringup.member.portfolio.school.domain.SchoolRepository;
import com.bringup.member.resume.domain.entity.*;
import com.bringup.member.resume.domain.repository.*;
import com.bringup.member.resume.domain.service.CVService;
import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.resume.dto.response.CVInsertResponseDto;
import com.bringup.member.resume.dto.response.CVListResponseDto;
import com.bringup.member.resume.dto.response.CVReadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService {

    private final CVRepository cvRepository;
    private final CVAwardRepository cvawardRepository;
    private final CVBlogRepository cvBlogRepository;
    private final CVCertificateRepository cvCertificateRepository;
    private final CVCareerRepository cvCareerRepository;
    private final CVSchoolRepository cvSchoolRepository;

    private final AwardRepository awardRepository;
    private final BlogRepository blogRepository;
    private final CertificateRepository certificateRepository;
    private final CareerRepository careerRepository;
    private final SchoolRepository schoolRepository;
    private final GithubRepository githubRepository;

    @Override
    public ResponseEntity<? super CVInsertResponseDto> insertCv(CVInsertRequestDto request, int userCode) {
        String skill = "";
        System.out.println("github:"+request.getGithub());
        if(request.getSkill() !=null){
            for(int i = 0; i<request.getSkill().size();i++){
                skill += request.getSkill().get(i);
                if(i!=request.getSkill().size()-1){
                    skill += ",";
                }
            }
        }

        CVEntity cvEntity = new CVEntity(request, skill, userCode);
        CVEntity cvEntitySave = cvRepository.save(cvEntity);
        int cvIndex = cvEntitySave.getCvIndex();

        if(request.getCvAward() != null) {
            for(int n : request.getCvAward()){
                CVAward cvAward = new CVAward(n, cvIndex);
                cvawardRepository.save(cvAward);
            }
        }
        if(request.getCvBlog() != null) {
            for(int n : request.getCvBlog()){
                CVBlog cvBlog = new CVBlog(n, cvIndex);
                cvBlogRepository.save(cvBlog);
            }
        }
        if(request.getCvCareer() != null) {
            for(int n : request.getCvCareer()){
                CVCareer cvCareer = new CVCareer(n, cvIndex);
                cvCareerRepository.save(cvCareer);
            }
        }
        if(request.getCvCertificate() != null) {
            for(int n : request.getCvCertificate()){
                CVCertificate cvCertificate = new CVCertificate(n, cvIndex);
                cvCertificateRepository.save(cvCertificate);
            }
        }
        if(request.getCvSchool() != null) {
            for(int n : request.getCvSchool()){
                CVSchool cvSchool = new CVSchool(n, cvIndex);
                cvSchoolRepository.save(cvSchool);
            }
        }
        if(request.getGithub() != null) {
            for(String n: request.getGithub()){
                GithubEntity githubEntity = new GithubEntity(n,cvIndex);
                githubRepository.save(githubEntity);
            }
        }
        return CVInsertResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CVReadResponseDto> readCV(String index) {
        int cvIndex = Integer.parseInt(index);

        CVEntity cvEntity = cvRepository.findByCvIndex(cvIndex);
        List<CVAward> cvAward = cvawardRepository.findByCvIndex(cvIndex);
        List<AwardEntity> awardlist  = new ArrayList<>();
        for(CVAward cvAwardEntity : cvAward){
            AwardEntity award = awardRepository.findById(cvAwardEntity.getId());
            awardlist.add(award);
        }
        List<CVBlog> cvBlog = cvBlogRepository.findByCvIndex(cvIndex);
        List<BlogEntity> bloglist  = new ArrayList<>();
        for(CVBlog cvBlogEntity : cvBlog){
            BlogEntity blog = blogRepository.findByBlogIndex(cvBlogEntity.getBlogIndex());
            bloglist.add(blog);
        }
        List<CVCareer> cvCareer = cvCareerRepository.findByCvIndex(cvIndex);
        List<CareerEntity> careerlist  = new ArrayList<>();
        for(CVCareer cvCareerEntity : cvCareer){
            CareerEntity career = careerRepository.findByCareerIndex(cvCareerEntity.getCareerIndex());
            careerlist.add(career);
        }
        List<CVCertificate> cvCertificate = cvCertificateRepository.findByCvIndex(cvIndex);
        List<CertificateEntity> certificatelist  = new ArrayList<>();
        for (CVCertificate certificateEntity : cvCertificate){
            CertificateEntity certificate = certificateRepository.findByCertificateIndex(certificateEntity.getCertificateIndex());
            certificatelist.add(certificate);
        }
        List<CVSchool> cvSchool = cvSchoolRepository.findByCvIndex(cvIndex);
        List<SchoolEntity> schoollist  = new ArrayList<>();
        for(CVSchool cvSchoolEntity : cvSchool){
            SchoolEntity school = schoolRepository.findBySchoolIndex(cvSchoolEntity.getSchoolIndex());
            schoollist.add(school);
        }
        List<GithubEntity> github = githubRepository.findByCvIndex(cvIndex);
        return CVReadResponseDto.success(cvEntity,awardlist,bloglist,careerlist,certificatelist,schoollist,github);
    }

    @Override
    public ResponseEntity<? super CVListResponseDto> listCv(int userCode) {
        List<CVEntity> list = null;
        try{
            list = cvRepository.findAllByUserIndex(userCode);
        }catch (Exception e){
            ResponseDto.databaseError();
        }

        return CVListResponseDto.success(list);
    }
}
