package com.bringup.company.advertisement.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemInfoResponseDto {
    private Integer itemIdx;
    private String itemName;
    private Integer itemPrice;
}
