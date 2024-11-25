package com.bringup.member.resume.domain.service.implement;

import com.bringup.member.resume.domain.entity.*;
import com.bringup.member.resume.domain.repository.*;
import com.bringup.member.resume.domain.service.CVService;
import com.bringup.member.resume.dto.request.CVInsertRequestDto;
import com.bringup.member.resume.dto.response.CVInsertResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService {

    private final CVRepository cvRepository;
    private final CVAwardRepository cvawardRepository;
    private final CVBlogRepository cvBlogRepository;
    private final CVCertificateRepository cvCertificateRepository;
    private final CVCareerRepository cvCareerRepository;
    private final CVSchoolRepository cvSchoolRepository;

    @Override
    public ResponseEntity<? super CVInsertResponseDto> insertCv(CVInsertRequestDto request, int userCode) {
        String skill = "";
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
        return CVInsertResponseDto.success();
    }

}
