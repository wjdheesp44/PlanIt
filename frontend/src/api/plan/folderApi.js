import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";

// Axios 인스턴스 생성
const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// 요청 인터셉터 - Access Token 자동 추가
axiosInstance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터 - 401 에러 시 토큰 갱신
axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = localStorage.getItem("refreshToken");
        const response = await axios.post(`${API_BASE_URL}/auth/refresh`, {
          refreshToken,
        });

        const { accessToken } = response.data;
        localStorage.setItem("accessToken", accessToken);

        originalRequest.headers.Authorization = `Bearer ${accessToken}`;
        return axiosInstance(originalRequest);
      } catch (refreshError) {
        // 리프레시 토큰도 만료된 경우 로그아웃
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        window.location.href = "/login";
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);

/**
 * 폴더(그룹) API
 */
export const folderApi = {
  /**
   * 내 폴더 목록 조회
   */
  getMyFolders: async () => {
    const response = await axiosInstance.get("/v1/plans");
    return response.data;
  },

  /**
   * 폴더 상세 조회
   */
  getFolderById: async (id) => {
    const response = await axiosInstance.get(`/v1/plans/${id}`);
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

    const response = await axiosInstance.post("/v1/plans", formData, {
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

    const response = await axiosInstance.put(`/v1/plans/${id}`, formData, {
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
    await axiosInstance.delete(`/v1/plans/${id}`);
  },
};
