import api from "../user/api";

/**
 * 그룹에 스팟 추가
 * @param {number} groupId - 그룹 ID
 * @param {number} spotId - 스팟 ID
 * @param {string} memo - 메모 (선택)
 * @returns {Promise}
 */
export const addSpotToGroup = async (groupId, spotId, memo = "") => {
  try {
    console.log("스팟 추가 요청:", { groupId, spotId, memo });

    const response = await api.post(`/api/v1/groups/${groupId}/spots/${spotId}`, {
      memo,
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

/**
 * 그룹의 스팟 목록 조회
 * @param {number} groupId - 그룹 ID
 * @returns {Promise}
 */
export const getGroupSpots = async (groupId) => {
  try {
    console.log("스팟 목록 조회 요청:", { groupId });

    const response = await api.get(`/api/v1/groups/${groupId}/plans`);

    console.log("스팟 목록 조회 응답:", response);
    return response.data;
  } catch (error) {
    console.error("스팟 목록 조회 API 오류:", error);
    throw error;
  }
};

/**
 * 플랜 수정 (메모, 순서)
 * @param {number} groupId - 그룹 ID
 * @param {number} planId - 플랜 ID
 * @param {Object} data - 수정할 데이터
 * @param {string} data.memo - 메모
 * @param {number} data.sortOrder - 순서
 * @returns {Promise}
 */
export const updatePlan = async (groupId, planId, { memo, sortOrder }) => {
  try {
    console.log("플랜 수정 요청:", { groupId, planId, memo, sortOrder });

    const response = await api.put(`/api/v1/groups/${groupId}/plans/${planId}`, {
      memo,
      sortOrder,
    });

    console.log("플랜 수정 응답:", response.data);
    return response.data;
  } catch (error) {
    console.error("플랜 수정 API 오류:", error);
    throw error;
  }
};

/**
 * 플랜 삭제
 * @param {number} groupId - 그룹 ID
 * @param {number} planId - 플랜 ID
 * @returns {Promise}
 */
export const deletePlan = async (groupId, planId) => {
  try {
    console.log("플랜 삭제 요청:", { groupId, planId });

    const response = await api.delete(`/api/v1/groups/${groupId}/plans/${planId}`);

    console.log("플랜 삭제 응답:", response.data);
    return response.data;
  } catch (error) {
    console.error("플랜 삭제 API 오류:", error);
    throw error;
  }
};
