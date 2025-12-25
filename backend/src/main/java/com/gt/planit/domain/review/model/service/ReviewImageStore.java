package com.gt.planit.domain.review.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ReviewImageStore {

    @Value("${file.upload.review}")
    private String uploadDir;

    public String save(MultipartFile file) {
        try {
            String originalName = file.getOriginalFilename();
            String ext = originalName.substring(originalName.lastIndexOf("."));
            String savedName = UUID.randomUUID() + ext;

            Path savePath = Paths.get(uploadDir, savedName);
            Files.createDirectories(savePath.getParent());

            file.transferTo(savePath.toFile());

            return "/uploads/reviews/" + savedName;

        } catch (IOException e) {
            throw new RuntimeException("리뷰 이미지 저장 실패", e);
        }
    }
}