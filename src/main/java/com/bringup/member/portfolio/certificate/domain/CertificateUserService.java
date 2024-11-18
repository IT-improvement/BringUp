package com.bringup.member.portfolio.certificate.domain;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.certificate.dto.CertificateListResponseDto;
import com.bringup.member.portfolio.certificate.dto.CertificateRequestDto;
import com.bringup.member.portfolio.certificate.dto.CertificateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateUserService {

    private final CertificateRepository certificateRepository;

    public ResponseEntity<? super CertificateListResponseDto> getList(int userIndex){
        List<CertificateEntity> list = null;
        try {
            list = certificateRepository.findByUserIndex(userIndex);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }
        return CertificateListResponseDto.success(list);
    }

    public ResponseEntity<? super CertificateResponseDto> insertCertificate(int userIndex, CertificateRequestDto dto){
        CertificateEntity entity = new CertificateEntity(userIndex, dto);
        try{
            certificateRepository.save(entity);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }
        return CertificateResponseDto.success();
    }

    public ResponseEntity<? super CertificateResponseDto> deleteCertificate(String index){
        int indexInt = Integer.parseInt(index);
        try{
            certificateRepository.deleteById(indexInt);
        }catch (Exception e){
            return ResponseDto.databaseError();
        }
        return CertificateResponseDto.success();
    }
}
