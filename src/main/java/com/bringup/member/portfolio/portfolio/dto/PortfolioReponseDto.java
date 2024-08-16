package com.bringup.member.portfolio.portfolio.dto;

import com.bringup.member.portfolio.portfolio.domain.PortfolioEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PortfolioReponseDto{

    private List<PortfolioEntity> list;

    public PortfolioReponseDto(List<PortfolioEntity> list) {
        this.list = list;
    }

    public static ResponseEntity<PortfolioReponseDto> success(List<PortfolioEntity> list){
        PortfolioReponseDto response = new PortfolioReponseDto(list);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
