package com.gt.planit.global.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String store(MultipartFile file, String category);
    void delete(String filePath);
    Resource load(String filePath);
}
