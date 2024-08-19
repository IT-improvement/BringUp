package com.bringup.common.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Value("${file.path}")
    private String filePath;
    @Value("${file.url}")
    private String fileUrl;
}
