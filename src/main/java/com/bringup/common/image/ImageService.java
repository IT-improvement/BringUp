package com.bringup.common.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Value("${file.path}")
    private String filePath;
    @Value("${file.url}")
    private String fileUrl;

    public String upLoadImage(MultipartFile file){
        String savePath = saveImage(file);
        return savePath;
    }
    // 이미지 저장
    private String saveImage(MultipartFile file){
        if(file.isEmpty()){
            logger.error("not images");
            return null;
        }
        String originalFileName = file.getOriginalFilename();
        //확장자 추출
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String savePath = filePath+saveFileName;

        try {
            file.transferTo(new File(savePath));
        }catch (Exception e){
            logger.error("error save images");
            e.printStackTrace();
            return null;
        }
        return fileUrl+saveFileName;
    }
    // 이미지 보기
    public Resource getImage(String fileNmae) {
        Resource resource = null;
        try {
            resource = new UrlResource("file:" + filePath + fileNmae);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resource;
    }
}
