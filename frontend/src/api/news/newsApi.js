import api from "@/api/user/api";

export const getSpotKeywords = (spotId, spotName) => {
  return api.get(`/api/v1/news/keywords/${spotId}`, {
    params: { spotName }
  });
};
