package com.bringup.common.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Value("${file.path}")
    private String filePath;

    @Value("${file.path.db}")
    private String savedPath;

    // 단일 이미지 업로드
    public String upLoadImage(MultipartFile file) {
        return saveImage(file);
    }

    // 여러 이미지 업로드 (DB에 저장될 경로들을 쉼표로 구분하여 반환)
    public String uploadImages(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            logger.error("No images provided");
            return null;
        }

        StringBuilder imagePaths = new StringBuilder();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String imagePath = saveImage(file);  // 실제 저장
                if (imagePath != null) {
                    if (imagePaths.length() > 0) {
                        imagePaths.append(",");  // 경로들 사이에 쉼표 추가
                    }
                    imagePaths.append(imagePath);
                }
            }
        }

        return imagePaths.toString();  // 쉼표로 구분된 이미지 경로 반환
    }

    // 이미지 저장
    public String saveImage(MultipartFile file) {
        if (file.isEmpty()) {
            logger.error("not images");
            return null;
        }
        String originalFileName = file.getOriginalFilename();
        //확장자 추출
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String savePath = filePath + saveFileName;

        try {
            Path path = Paths.get(savePath).normalize();
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path);
        } catch (Exception e) {
            logger.error("error save images");
            e.printStackTrace();
            return null;
        }
        System.out.println("fileName: " + saveFileName);
        //return saveFileName;
        // DB에 저장되는 값이 경로이려면 savePath 이름값이려면 saveFileName
        //return savePath;
        return savedPath + saveFileName;
    }

    // 이미지 보기
    public Resource getImage(String fileName) {
        try {
            Path file = Paths.get(filePath + fileName).normalize();
            System.out.println("File path: " + file.toString()); // 파일 경로 출력
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file! Path: " + file.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String saveBase64Image(String base64Data, String fileName) throws IOException {
        // Base64 데이터를 디코딩
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

        // 확장자 추출 (fileName에서)
        String extension = fileName.substring(fileName.lastIndexOf("."));

        // UUID로 고유한 파일 이름 생성
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;

        // 저장 경로 설정
        Path savePath = Paths.get(filePath + saveFileName);

        // 파일 저장
        try (FileOutputStream fos = new FileOutputStream(savePath.toFile())) {
            fos.write(decodedBytes);
        }

        // 저장된 파일 경로 반환
        return savedPath + saveFileName;
    }

}