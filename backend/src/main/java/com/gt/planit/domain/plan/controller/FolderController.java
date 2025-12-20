package com.gt.planit.domain.plan.controller;

import com.gt.planit.domain.plan.model.dto.FolderReqDto;
import com.gt.planit.domain.plan.model.dto.FolderResDto;
import com.gt.planit.domain.plan.model.service.FolderService;
import org.springframework.web.bind.annotation.RestController;
import com.gt.planit.global.file.FileStorageService;
import com.gt.planit.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/plans")
@RequiredArgsConstructor
@Slf4j
public class FolderController {

    private final FolderService folderService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FolderResDto> createFolder(
            @RequestParam("name") String name,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();

        log.info("Creating folder: name={}, hasThumbnail={}, userId={}",
                name, thumbnail != null && !thumbnail.isEmpty(), userId);

        String thumbnailPath = null;
        if (thumbnail != null && !thumbnail.isEmpty()) {
            thumbnailPath = fileStorageService.store(thumbnail, "folder");
        }

        FolderResDto folder = folderService.createFolder(
                userId,
                name,
                thumbnailPath
        );

        return ResponseEntity
                .created(URI.create("/api/v1/plans/" + folder.getId()))
                .body(folder);
    }

    @GetMapping
    public ResponseEntity<List<FolderResDto>> getMyFolders(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        List<FolderResDto> folders = folderService.getMyFolders(userId);
        return ResponseEntity.ok(folders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolderResDto> getFolderById(@PathVariable Long id) {
        FolderResDto folder = folderService.getFolderById(id);
        return ResponseEntity.ok(folder);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FolderResDto> updateFolder(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();

        String thumbnailPath = null;
        if (thumbnail != null && !thumbnail.isEmpty()) {
            thumbnailPath = fileStorageService.store(thumbnail, "folder");
        }

        FolderResDto folder = folderService.updateFolder(
                id,
                userId,
                name,
                thumbnailPath
        );

        return ResponseEntity
                .created(URI.create("/api/v1/plans/" + folder.getId()))
                .body(folder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();
        folderService.deleteFolder(id, userId);
        return ResponseEntity.noContent().build();
    }
}