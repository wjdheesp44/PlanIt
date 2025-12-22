import api from "@/api/user/api";

export const getLikedSpots = () => {
  return api.get("/api/v1/my-page/likes");
};
