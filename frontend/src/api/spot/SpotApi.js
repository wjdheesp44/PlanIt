import api from "../user/api";

const spotApi = {
  /**
   * Get spots with filters
   * @param {Object} params - Filter parameters
   * @returns {Promise} API response
   */
  getSpots: async (params = {}) => {
    try {
      const config = {
        params: {
          type: params.type || [],
          "start-date": params.startDate || undefined,
          "end-date": params.endDate || undefined,
          weather: params.weather ?? undefined,
          rating: params.rating ?? undefined,
          "likes-count": params.likesCount || undefined,
          search: params.search || undefined,
          tag: params.tag ?? undefined,
          region: params.region ?? undefined,
          sort: params.sort || "LATEST",
          page: params.page ?? 1,
          size: params.size || 18,
        },
        paramsSerializer: {
          indexes: null,
        },
      };

      const response = await api.get("/api/v1/spots", config);
      return response.data;
    } catch (error) {
      console.error("Failed to fetch spots:", error);
      throw error;
    }
  },

  /**
   * Toggle favorite status
   * @param {number} spotId - Spot ID
   * @returns {Promise} API response
   */
  toggleFavorite: async (spotId) => {
    try {
      const response = await api.post(`/api/v1/spots/${spotId}/favorite`);
      return response.data;
    } catch (error) {
      console.error("Failed to toggle favorite:", error);
      throw error;
    }
  },

  /**
   * 스팟 상세 조회
   */
  getSpotById: async (spotId) => {
    try {
      const response = await api.get(`/api/v1/spots/${spotId}`);
      return response.data;
    } catch (error) {
      console.error("Failed to toggle favorite:", error);
      throw error;
    }
  },
};

export default spotApi;
