import axios from "axios";

const authApi = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true, // 세션 / refresh-token 쿠키 쓸 경우
  headers: {
    "Content-Type": "application/json",
  },
});

export default authApi;
