// api.js
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

/* 요청 인터셉터 */
api.interceptors.request.use(config => {
  const accessToken = localStorage.getItem("accessToken");

  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  // multipart 요청이면 Content-Type 건들지 않음
    if (!(config.data instanceof FormData)) {
      config.headers["Content-Type"] = "application/json";
    }

  return config;
});

/* 응답 인터셉터 */
let isRefreshing = false;
let retryQueue = [];

api.interceptors.response.use(
  response => response,

  async error => {
    const originalRequest = error.config;

    // access 만료 (401) + 재시도 아님
    if (error.response?.status === 401 && !originalRequest._retry) {

      // 중복 refresh 방지
      if (isRefreshing) {
        return new Promise(resolve => {
          retryQueue.push(token => {
            originalRequest.headers.Authorization = `Bearer ${token}`;
            resolve(api(originalRequest));
          });
        });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      try {
        const refreshToken = localStorage.getItem("refreshToken");

        const res = await axios.post(
          "/auth/refresh",
          {},
          {
            headers: {
              Authorization: `Bearer ${refreshToken}`,
            },
          }
        );

        const newAccessToken = res.data.accessToken;
        localStorage.setItem("accessToken", newAccessToken);

        // 대기 중 요청들 재실행
        retryQueue.forEach(cb => cb(newAccessToken));
        retryQueue = [];

        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        return api(originalRequest);

      } catch (e) {
        // refresh 실패 → 강제 로그아웃
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        window.location.href = "/login";
        return Promise.reject(e);

      } finally {
        isRefreshing = false;
      }
    }

    return Promise.reject(error);
  }
);

export default api;
