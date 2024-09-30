package com.bringup.member.portfolio.portfolio.domain;

import com.bringup.member.portfolio.portfolio.dto.PortfolioInsertResponseDto;
import com.bringup.member.portfolio.portfolio.dto.PortfolioReponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public ResponseEntity<? super PortfolioReponseDto> portfolioList(String userIndex){
        int userIndexInt = Integer.parseInt(userIndex);
        List<PortfolioEntity> list = portfolioRepository.findByUserIndex(userIndexInt);
        return PortfolioReponseDto.success(list);
    }

    public ResponseEntity<? super PortfolioInsertResponseDto> insertPortfolio(){

        return PortfolioInsertResponseDto.success();
    }
}
