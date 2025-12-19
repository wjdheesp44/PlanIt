package com.gt.planit.domain.plan.model.service;

import com.gt.planit.domain.plan.model.dto.FolderReqDto;
import com.gt.planit.domain.plan.model.dto.FolderResDto;

import java.util.List;

public interface FolderService {
    /**
     * 폴더 생성
     */
    FolderResDto createFolder(String username, FolderReqDto request, String thumbnailPath);

    /**
     * 내 폴더 목록 조회
     */
    List<FolderResDto> getMyFolders(String username);

    /**
     * 폴더 상세 조회
     */
    FolderResDto getFolderById(Long id);

    /**
     * 폴더 수정
     */
    FolderResDto updateFolder(Long id, String username, FolderReqDto request, String thumbnailPath);

    /**
     * 폴더 삭제
     */
    void deleteFolder(Long id, String username);
}
