import api from "@/api/user/api";

export const getLikedSpots = () => {
  return api.get("/api/v1/my-page/likes");
};

// 후기 좋아요(도움이 돼요) 토글
export const toggleReviewHelpful = (reviewId) => {
  return api.post(`/api/v1/reviews/${reviewId}/likes`);
};
