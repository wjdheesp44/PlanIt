import api from "@/api/user/api";

export default {
  getReviews(spotId) {
    return api.get(`/api/v1/spots/${spotId}/reviews`);
  },
  createReview(spotId, data) {
    return api.post(`/api/v1/spots/${spotId}/reviews`, data);
  },
  updateReview(reviewId, data) {
    return api.put(`/api/v1/my-page/reviews/${reviewId}`, data);
  },
  deleteReview(reviewId) {
    return api.delete(`/api/v1/my-page/reviews/${reviewId}`);
  },
  getMyReviews() {
    return api.get(`/api/v1/my-page/reviews`);
  },
};
