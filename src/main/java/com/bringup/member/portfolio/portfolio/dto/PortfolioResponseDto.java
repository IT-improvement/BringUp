package com.bringup.member.portfolio.portfolio.dto;

import com.bringup.member.portfolio.portfolio.domain.PortfolioEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class PortfolioResponseDto {

    private List<PortfolioEntity> list;

    public PortfolioResponseDto(List<PortfolioEntity> list) {
        this.list = list;
    }

    public static ResponseEntity<PortfolioResponseDto> success(List<PortfolioEntity> list){
        PortfolioResponseDto response = new PortfolioResponseDto(list);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
