package com.bringup.company.advertisement.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ChoicedateRequestDto {
    private String startDate;
    private String endDate;
    private String timeSlot;
}
