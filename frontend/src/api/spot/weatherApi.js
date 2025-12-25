import api from "../user/api";

const weatherApi = {
  /**
   * 구군별 날씨 캘린더 조회 (10일치)
   */
  getWeatherCalendar(gugunId, startDate, endDate) {
    return api.get(`/api/v1/weather/calendar/${gugunId}`, {
      params: {
        startDate,
        endDate,
      },
    });
  },

  /**
   * 특정 날짜 시간별 상세 예보
   */
  getWeatherDetail(gugunId, date) {
    return api.get(`/api/v1/weather/detail/${gugunId}`, {
      params: {
        date,
      },
    });
  },

  /**
   * 여행 추천 날짜 조회
   */
  getRecommendedDates(gugunId, startDate, endDate) {
    return api.get(`/api/v1/weather/recommend/${gugunId}`, {
      params: {
        startDate,
        endDate,
      },
    });
  },
};

export default weatherApi;
