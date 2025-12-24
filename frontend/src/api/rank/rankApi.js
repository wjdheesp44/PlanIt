import api from "@/api/user/api";

export const getSpotRanking = (limit = 10) =>
  api.get("/api/v1/spots/ranking/main", { params: { limit } });

export const getPopupRanking = (limit = 10) =>
  api.get("/api/v1/spots/ranking/popup", { params: { limit } });
