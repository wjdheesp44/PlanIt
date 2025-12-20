import api from "../user/api";

/**
 * 폴더(그룹) API
 */
export const folderApi = {
  /**
   * 내 폴더 목록 조회
   */
  getMyFolders: async () => {
    const response = await api.get("/api/v1/plans");
    return response.data;
  },

  /**
   * 폴더 상세 조회
   */
  getFolderById: async (id) => {
    const response = await api.get(`/api/v1/plans/${id}`);
    return response.data;
  },

  /**
   * 폴더 생성
   * @param {string} name - 폴더 이름
   * @param {File} thumbnail - 썸네일 이미지 (선택)
   */
  createFolder: async (name, thumbnail) => {
    const formData = new FormData();
    formData.append("name", name);

    if (thumbnail) {
      formData.append("thumbnail", thumbnail);
    }

    const response = await api.post("/api/v1/plans", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  },

  /**
   * 폴더 수정
   */
  updateFolder: async (id, name, thumbnail) => {
    const formData = new FormData();
    formData.append("name", name);

    if (thumbnail) {
      formData.append("thumbnail", thumbnail);
    }

    const response = await api.put(`/api/v1/plans/${id}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  },

  /**
   * 폴더 삭제
   */
  deleteFolder: async (id) => {
    await api.delete(`/api/v1/plans/${id}`);
  },
};
