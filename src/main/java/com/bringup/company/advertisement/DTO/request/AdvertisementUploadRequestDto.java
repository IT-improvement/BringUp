package com.bringup.company.advertisement.DTO.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AdvertisementUploadRequestDto {
    private int advertisementIndex;
    private MultipartFile image;
}