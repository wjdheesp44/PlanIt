import { defineStore } from "pinia";
import api from "@/api/user/api";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    accessToken: localStorage.getItem("accessToken") || null,
    user: null, // { email, nickname, name, role ... }
  }),

  getters: {
    isLogin: (state) => !!state.accessToken,
  },

  actions: {
    setLogin(token, user) {
      this.accessToken = token;
      this.user = user;
      localStorage.setItem("accessToken", token);
    },

    async fetchMe() {
      if (!this.accessToken) return;

      try {
        const res = await api.get("/api/auth/me");
        this.user = res.data;
      } catch (e) {
        this.logout(); // 토큰 만료
      }
    },

    logout() {
      this.accessToken = null;
      this.user = null;
      localStorage.removeItem("accessToken");
    },
  },
});
