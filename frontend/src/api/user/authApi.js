import axios from "axios";

const authApi = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});

// 이메일 중복 체크
export const checkEmailApi = (email) => {
  return authApi.get(`/api/check-email?email=${email}`);
};

// 닉네임 중복 체크
export const checkNicknameApi = (nickname) => {
  return authApi.get(`/api/check-nickname?nickname=${nickname}`);
};

export default authApi;
