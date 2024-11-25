/*
package com.bringup.member.resume.domain.service.implement;

import com.bringup.common.response.ResponseDto;
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

    @Override
    public ResponseEntity<? super CVReadResponseDto> readCV(String index) {
        int cvIndex = Integer.parseInt(index);

        CVEntity cvEntity = cvRepository.findByCvIndex(cvIndex);
        List<CVAward> cvAward = cvawardRepository.findByCvIndex(cvIndex);
        List<CVBlog> cvBlog = cvBlogRepository.findByCvIndex(cvIndex);
        List<CVCareer> cvCareer = cvCareerRepository.findByCvIndex(cvIndex);
        List<CVCertificate> cvCertificate = cvCertificateRepository.findByCvIndex(cvIndex);
        List<CVSchool> cvSchool = cvSchoolRepository.findByCvIndex(cvIndex);

        return CVReadResponseDto.success(cvEntity,cvAward,cvBlog,cvCareer,cvCertificate,cvSchool);
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
*/
