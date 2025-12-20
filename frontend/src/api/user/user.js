import api from "@/api/user/api.js";

export const getMyInfo = () => {
  return api.get("/api/v1/profile");
};
