package com.gt.planit.domain.plan.model.service;

import com.gt.planit.domain.plan.model.dto.FolderReqDto;
import com.gt.planit.domain.plan.model.dto.FolderResDto;

import java.util.List;

public interface FolderService {
    /**
     * 폴더 생성
     * @param userId 사용자 ID
     * @param thumbnailPath 썸네일 파일 경로
     * @return 생성된 폴더 정보
     */
    FolderResDto createFolder(Long userId, String name, String thumbnailPath);

    /**
     * 내 폴더 목록 조회
     * @param userId 사용자 ID
     * @return 폴더 목록
     */
    List<FolderResDto> getMyFolders(Long userId);

    /**
     * 폴더 상세 조회
     * @param id 폴더 ID
     * @return 폴더 정보
     */
    FolderResDto getFolderById(Long id, Long userId);

    /**
     * 폴더 수정
     * @param id 폴더 ID
     * @param userId 사용자 ID
     * @param request 수정 요청 정보
     * @param thumbnailPath 새 썸네일 파일 경로 (nullable)
     * @return 수정된 폴더 정보
     */
    FolderResDto updateFolder(Long id, Long userId, String name, String thumbnailPath);

    /**
     * 폴더 삭제
     * @param id 폴더 ID
     * @param userId 사용자 ID
     */
    void deleteFolder(Long id, Long userId);
}
