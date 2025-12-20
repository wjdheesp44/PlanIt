package com.gt.planit.global.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class LocalFileStorageService implements FileStorageService {

    private final Path uploadPath;
    private final long maxFileSize;
    private final Set<String> allowedExtensions;

    public LocalFileStorageService(
            @Value("${file.upload.dir}") String uploadDir,
            @Value("${file.upload.max-size}") long maxFileSize,
            @Value("${file.upload.allowed-extensions}") String extensions
    ) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.maxFileSize = maxFileSize;
        this.allowedExtensions = Set.of(extensions.split(","));

        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    @Override
    public String store(MultipartFile file, String category) {
        validateFile(file);

        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename);
        String storedFilename = generateFilename(category, extension);

        try {
            Path targetLocation = this.uploadPath.resolve(storedFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("File stored: {}", storedFilename);
            return storedFilename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public void delete(String filename) {
        try {
            Path filePath = this.uploadPath.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
            log.info("File deleted: {}", filename);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", filename, e);
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path filePath = this.uploadPath.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + filename, e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds limit");
        }

        String extension = getExtension(file.getOriginalFilename());
        if (!allowedExtensions.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("File type not allowed");
        }
    }

    private String getExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return lastDot > 0 ? filename.substring(lastDot + 1) : "";
    }

    private String generateFilename(String category, String extension) {
        return String.format("%s_%d_%s.%s",
                category,
                System.currentTimeMillis(),
                UUID.randomUUID().toString().substring(0, 8),
                extension
        );
    }
}