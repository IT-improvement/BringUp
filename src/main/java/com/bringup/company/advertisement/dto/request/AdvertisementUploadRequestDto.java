package com.bringup.company.advertisement.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AdvertisementUploadRequestDto {
    private Integer advertisementIndex;
    private MultipartFile image;
}