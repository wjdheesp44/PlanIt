import api from "../user/api";

/**
 * 스팟 검색 API
 * @param {Object} params - 검색 파라미터
 * @param {Array<string>} params.types - 스팟 타입 배열 (ATTRACTION, FESTIVAL, POPUP)
 * @param {string} params.search - 검색어
 * @param {string} params.sort - 정렬 기준 (LIKES_DESC, CREATED_DESC 등)
 * @param {number} params.page - 페이지 번호 (1부터 시작)
 * @param {number} params.size - 페이지 크기
 * @returns {Promise} API 응답
 */
export const searchSpots = async ({
  types = ["ATTRACTION", "FESTIVAL", "POPUP"],
  search = "",
  sort = "LIKES_DESC",
  page = 1,
  size = 10,
}) => {
  try {
    const params = new URLSearchParams();

    // 타입 파라미터 추가
    types.forEach((type) => {
      params.append("type", type);
    });

    // 검색어가 있으면 추가
    if (search && search.trim()) {
      params.append("search", search.trim());
    }

    params.append("sort", sort);
    params.append("page", page);
    params.append("size", size);

    console.log("검색 요청:", `/spots?${params.toString()}`);

    const response = await api.get(`/api/v1/spots?${params.toString()}`);
    console.log("검색 응답:", response.data);

    return response.data;
  } catch (error) {
    console.error("스팟 검색 API 오류:", error);
    if (error.response) {
      console.error("응답 데이터:", error.response.data);
      console.error("응답 상태:", error.response.status);
    } else if (error.request) {
      console.error("요청이 전송되었으나 응답이 없음:", error.request);
    } else {
      console.error("요청 설정 중 오류:", error.message);
    }
    throw error;
  }
};

/**
 * 폴더에 스팟 추가 API
 * @param {number} folderId - 폴더 ID
 * @param {number} spotId - 스팟 ID
 * @returns {Promise} API 응답
 */
export const addSpotToFolder = async (folderId, spotId) => {
  try {
    console.log("스팟 추가 요청:", { folderId, spotId });

    const response = await api.post(`/api/v1/folders/${folderId}/spots`, {
      spotId,
    });

    console.log("스팟 추가 응답:", response.data);
    return response.data;
  } catch (error) {
    console.error("스팟 추가 API 오류:", error);
    if (error.response) {
      console.error("응답 데이터:", error.response.data);
      console.error("응답 상태:", error.response.status);
    }
    throw error;
  }
};
