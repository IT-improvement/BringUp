package com.bringup.member.portfolio.portfolio.domain;

import com.bringup.member.portfolio.portfolio.dto.PortfolioReponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public ResponseEntity<? super PortfolioReponseDto> portfolioList(String userIndex){
        ArrayList<PortfolioEntity> list = portfolioRepository.findByUserIndex(userIndex);
        return PortfolioReponseDto.success(list);
    }
}
