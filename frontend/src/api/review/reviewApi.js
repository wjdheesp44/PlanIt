import api from "@/api/user/api";

export default {
  getReviews(spotId) {
    return api.get(`/api/v1/spots/${spotId}/reviews`);
  },
  createReview(spotId, data) {
    return api.post(`/api/v1/spots/${spotId}/reviews`, data);
  },
  updateReview(spotId, reviewId, data) {
    return api.put(`/api/v1/spots/${spotId}/reviews/${reviewId}`, data);
  },
  deleteReview(spotId, reviewId) {
    return api.delete(`/api/v1/spots/${spotId}/reviews/${reviewId}`);
  },
  getMyReviews(userId) {
    return api.get(`/api/v1/my-page/reviews/${userId}`);
  },
};
