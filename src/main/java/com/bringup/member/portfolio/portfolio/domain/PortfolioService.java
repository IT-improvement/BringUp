package com.bringup.member.portfolio.portfolio.domain;

import com.bringup.member.portfolio.portfolio.dto.PortfolioInsertResponseDto;
import com.bringup.member.portfolio.portfolio.dto.PortfolioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public ResponseEntity<? super PortfolioResponseDto> portfolioList(String userIndex){
        int userIndexInt = Integer.parseInt(userIndex);
        List<PortfolioEntity> list = portfolioRepository.findByUserIndex(userIndexInt);
        return PortfolioResponseDto.success(list);
    }

    public ResponseEntity<? super PortfolioInsertResponseDto> insertPortfolio(){

        return PortfolioInsertResponseDto.success();
    }
}
