package com.gt.planit.domain.plan.model.service;

import com.gt.planit.domain.plan.model.dto.FolderDto;
import com.gt.planit.domain.plan.model.dto.FolderReqDto;
import com.gt.planit.domain.plan.model.dto.FolderResDto;
import com.gt.planit.domain.plan.model.mapper.FolderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final FolderMapper folderMapper;

    @Override
    @Transactional
    public FolderResDto createFolder(Long userId, String name, String thumbnailPath) {
        FolderDto folderDto = FolderDto.builder()
                .userId(userId)
                .name(name)
                .thumbnailPath(thumbnailPath)
                .build();

        folderMapper.insert(folderDto);
        log.info("Folder created: id={}, userId={}, name={}", folderDto.getId(), userId, folderDto.getName());

        return FolderResDto.from(folderMapper.findById(folderDto.getId()).orElseThrow());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FolderResDto> getMyFolders(Long userId) {
        List<FolderDto> folders = folderMapper.findByUserId(userId);

        return folders.stream()
                .map(FolderResDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FolderResDto getFolderById(Long id) {
        FolderDto folderDto = folderMapper.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found: " + id));
        return FolderResDto.from(folderDto);
    }

    @Override
    @Transactional
    public FolderResDto updateFolder(Long id, Long userId, String name, String thumbnailPath) {
        FolderDto folderDto = folderMapper.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found: " + id));

        // 권한 확인
        if (!folderDto.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Not authorized to update this folder");
        }

        FolderDto updatedFolder = FolderDto.builder()
                .id(id)
                .userId(userId)
                .name(name)
                .thumbnailPath(thumbnailPath != null ? thumbnailPath : folderDto.getThumbnailPath())
                .build();

        folderMapper.update(updatedFolder);
        log.info("Folder updated: id={}, name={}", id, name);

        return FolderResDto.from(folderMapper.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void deleteFolder(Long id, Long userId) {
        FolderDto folderDto = folderMapper.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found: " + id));

        // 권한 확인
        if (!folderDto.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Not authorized to delete this folder");
        }

        folderMapper.deleteById(id);
        log.info("Folder deleted: id={}", id);
    }
}