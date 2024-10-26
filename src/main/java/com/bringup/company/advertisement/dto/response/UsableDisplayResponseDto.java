package com.bringup.company.advertisement.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UsableDisplayResponseDto {
    private Set<LocalDate> nonDisplay;
    private Integer itemIdx;
    private String itemName;
    private Integer itemPrice;
}
