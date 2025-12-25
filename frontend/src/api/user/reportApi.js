import api from "../user/api";

/**
 * 사용자 분석 리포트 조회
 * - 로그인한 사용자의 리포트 조회
 * - 이미 AI 분석이 있으면 DB에서 가져오기
 * - 없으면 새로 생성
 */
export const getReport = async () => {
  try {
    const response = await api.get("/api/v1/report");
    return response.data;
  } catch (error) {
    console.error("리포트 조회 실패:", error);
    throw error;
  }
};

/**
 * AI 분석 재생성
 * - 로그인한 사용자의 AI 분석 재생성
 */
export const regenerateAiAnalysis = async () => {
  try {
    const response = await api.post("/api/v1/report/regenerate");
    return response.data;
  } catch (error) {
    console.error("AI 분석 재생성 실패:", error);
    throw error;
  }
};
