package com.bringup.common.image;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Value("${file.url}")
    private String fileUrl;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        String fileName = imageService.upLoadImage(file);
        return fileUrl+ fileName;
    }

    @GetMapping(value = "{fileName}", produces ={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getImage(@PathVariable("fileName") String fileName){
        Resource resource = imageService.getImage(fileName);
        return resource;
    }
}
