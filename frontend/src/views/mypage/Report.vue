<template>
  <!-- 로딩 상태 -->
  <div v-if="loading" class="loading-container">
    <div class="loading-spinner"></div>
    <p class="loading-text">리포트를 생성하고 있습니다...</p>
  </div>

  <!-- 리포트가 없을 때: EmptyReport 표시 -->
  <EmptyReport v-else-if="!hasReport" @start-analysis="startAnalysis" />

  <!-- 리포트가 있을 때: 기존 리포트 표시 -->
  <div v-else class="report-page">
    <!-- 여행 스타일 배너 -->
    <div class="travel-style-banner" :class="`style-${travelStyle.type?.toLowerCase()}`">
      <div class="style-icon">{{ travelStyle.icon }}</div>
      <div class="style-content">
        <h2 class="style-title">{{ travelStyle.name }}</h2>
        <p class="style-description">{{ travelStyle.description }}</p>
      </div>
    </div>

    <!-- AI 분석 섹션 -->
    <div class="ai-analysis-section">
      <div class="section-header">
        <h3 class="section-title">🤖 AI 여행 분석</h3>
        <button class="ai-regenerate-btn" @click="regenerateAnalysis" :disabled="regenerating">
          <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
            <path
              d="M9.181 2.345c.036-.191.137-.364.287-.488.15-.124.338-.192.533-.192.194 0 .383.068.532.192.15.124.251.297.287.488l.876 4.632c.062.329.222.632.459.869.237.237.54.397.869.46l4.632.875c.191.036.364.137.488.287.124.15.192.338.192.532 0 .195-.068.383-.192.533-.124.15-.297.251-.488.287l-4.632.875c-.329.062-.632.222-.869.459-.237.237-.397.54-.46.869l-.875 4.632c-.036.191-.137.364-.287.488-.15.124-.338.192-.533.192-.194 0-.383-.068-.532-.192-.15-.124-.251-.297-.287-.488l-.876-4.632c-.062-.329-.222-.632-.459-.869-.237-.237-.54-.397-.869-.46l-4.632-.875c-.191-.036-.364-.137-.488-.287-.124-.15-.192-.338-.192-.532 0-.195.068-.383.192-.533.124-.15.297-.251.488-.287l4.632-.875c.329-.062.632-.222.869-.459.237-.237.397-.54.46-.869l.875-4.632z"
              stroke="currentColor"
              stroke-width="1.667"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M16.667 1.667v3.333M18.333 3.333H15"
              stroke="currentColor"
              stroke-width="1.667"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M3.334 18.333a1.667 1.667 0 100-3.333 1.667 1.667 0 000 3.333z"
              stroke="currentColor"
              stroke-width="1.667"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
          <span v-if="!regenerating">AI 분석 다시하기</span>
          <span v-else>생성 중...</span>
        </button>
      </div>
      <p class="ai-analysis-text">{{ aiAnalysis }}</p>
    </div>

    <!-- 통계 카드 그리드 -->
    <div class="stats-grid">
      <div
        v-for="stat in statistics"
        :key="stat.id"
        class="stat-card"
        :class="`stat-card-${stat.color}`"
      >
        <div class="stat-header">
          <div class="stat-icon" v-html="stat.icon"></div>
          <h3 class="stat-label">{{ stat.label }}</h3>
        </div>
        <p class="stat-value">{{ stat.value }}</p>
      </div>
    </div>

    <!-- 하단 섹션 -->
    <div class="bottom-section">
      <!-- 지역별 분포 -->
      <div class="preference-card">
        <h2 class="section-title">자주 찾는 지역</h2>
        <div class="category-list">
          <div v-for="region in regions" :key="region.sidoName" class="category-item">
            <div class="category-header">
              <span class="category-name">{{ region.sidoName }}</span>
              <span class="category-stats">{{ region.count }}개 ({{ region.percentage }}%)</span>
            </div>
            <div class="progress-bar">
              <div class="progress-fill" :style="{ width: region.percentage + '%' }"></div>
            </div>
          </div>
        </div>
      </div>

      <!-- 콘텐츠 타입별 선호도 -->
      <div class="preference-card">
        <h2 class="section-title">선호 콘텐츠 타입</h2>
        <div class="category-list">
          <div v-for="content in contentTypes" :key="content.contentType" class="category-item">
            <div class="category-header">
              <span class="category-name">{{ getContentTypeName(content.contentType) }}</span>
              <span class="category-stats">{{ content.count }}개 ({{ content.percentage }}%)</span>
            </div>
            <div class="progress-bar">
              <div
                class="progress-fill"
                :class="`fill-${content.contentType.toLowerCase()}`"
                :style="{ width: content.percentage + '%' }"
              ></div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- AI 추천 배너 -->
    <div class="ai-recommendation">
      <div class="ai-icon">
        <svg width="17" height="17" viewBox="0 0 17 17" fill="none">
          <path
            d="M10.911 4.774h4.092v4.092M15.004 4.774l-5.797 5.797-3.41-3.41L1.364 11.593"
            stroke="#9810FA"
            stroke-width="1.364"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </div>
      <div class="ai-content">
        <h3 class="ai-title">AI 추천</h3>
        <p class="ai-text">{{ aiRecommendation }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import EmptyReport from "@/components/mypage/EmptyReport.vue";
import { getReport, regenerateAiAnalysis } from "@/api/user/reportApi";

const router = useRouter();

// 상태 관리
const loading = ref(false);
const regenerating = ref(false);
const hasReport = ref(false);

// 리포트 데이터
const travelStyle = ref({});
const statistics = ref([]);
const regions = ref([]);
const contentTypes = ref([]);
const aiAnalysis = ref("");
const aiRecommendation = ref("");

/**
 * 리포트 조회
 * - @AuthenticationPrincipal로 서버에서 userId 자동 처리
 */
const fetchReport = async () => {
  loading.value = true;

  try {
    // userId 파라미터 없이 호출 (서버에서 인증 정보로 처리)
    const data = await getReport();

    // 여행 스타일
    travelStyle.value = data.travelStyle;

    // 통계 카드 데이터 구성
    statistics.value = [
      {
        id: 1,
        label: "저장한 스팟",
        value: data.statistics.totalSpots,
        color: "blue",
        icon: `<svg width="20" height="20" viewBox="0 0 20 20" fill="none">
          <path d="M16.666 8.333c0 4.161-4.616 8.494-6.166 9.833-.144.087-.32.134-.5.134-.181 0-.357-.047-.501-.134-1.55-1.34-6.166-5.672-6.166-9.833 0-1.768.702-3.464 1.952-4.714C6.536 2.369 8.232 1.667 10 1.667c1.768 0 3.464.702 4.714 1.952 1.25 1.25 1.952 2.946 1.952 4.714z" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
          <path d="M10 10.833a2.5 2.5 0 100-5 2.5 2.5 0 000 5z" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>`,
      },
      {
        id: 2,
        label: "좋아요한 스팟",
        value: data.statistics.totalLikes,
        color: "red",
        icon: `<svg width="20" height="20" viewBox="0 0 20 20" fill="none">
          <path d="M1.667 7.917c.002-.928.282-1.834.807-2.598.524-.763 1.24-1.35 2.066-1.69.826-.34 1.733-.41 2.61-.199.878.21 1.682.694 2.315 1.386.044.046.097.083.155.108.058.025.121.038.185.038s.127-.013.185-.038c.058-.025.111-.062.155-.108.618-.706 1.422-1.196 2.301-1.405.879-.21 1.786-.148 2.614.139.828.287 1.545.878 2.068 1.645.523.767.801 1.675.797 2.604 0 1.908-1.25 3.333-2.5 4.583l-4.576 4.428c-.156.185-.346.338-.561.452-.215.115-.448.19-.688.223-.24.032-.484.022-.719-.032-.236-.054-.46-.15-.66-.283l-3.833-3.563c-1.25-1.25-2.5-2.667-2.5-4.583z" fill="currentColor" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>`,
      },
      {
        id: 3,
        label: "작성한 리뷰",
        value: data.statistics.totalReviews,
        color: "purple",
        icon: `<svg width="20" height="20" viewBox="0 0 20 20" fill="none">
          <path d="M2.5 2.5v13.333c0 .442.176.866.488 1.179.313.312.737.488 1.179.488H17.5" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
          <path d="M15 14.167V7.5M10.833 14.167V4.167M6.667 14.167v-2.5" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>`,
      },
      {
        id: 4,
        label: "평균 평점",
        value: data.statistics.avgRating?.toFixed(1) || "0.0",
        color: "yellow",
        icon: `<svg width="20" height="20" viewBox="0 0 20 20" fill="none">
          <path d="M10 1.667l2.575 5.216 5.758.841-4.166 4.059.983 5.734L10 14.85l-5.15 2.667.983-5.734-4.166-4.059 5.758-.841L10 1.667z" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>`,
      },
    ];

    // 지역/콘텐츠 데이터
    regions.value = data.regions || [];
    contentTypes.value = data.contentTypes || [];

    // AI 분석
    aiAnalysis.value = data.aiAnalysis || "";
    aiRecommendation.value = data.aiRecommendation || "";

    // 리포트 있음으로 설정
    hasReport.value = true;
  } catch (error) {
    console.error("리포트 조회 실패:", error);

    // 401 Unauthorized면 로그인 페이지로
    if (error.response?.status === 401) {
      alert("로그인이 필요합니다.");
      router.push("/login");
      return;
    }

    // 에러 시 빈 리포트 표시
    hasReport.value = false;
  } finally {
    loading.value = false;
  }
};

/**
 * AI 분석 시작 (EmptyReport에서 호출)
 * - 첫 리포트 생성
 */
const startAnalysis = async () => {
  loading.value = true;

  try {
    // API 호출하여 리포트 생성
    // 서버에서 DB에 없으면 새로 생성하고, 있으면 가져옴
    await fetchReport(false); // 이미 loading.value = true이므로 중복 방지
  } catch (error) {
    console.error("AI 분석 시작 실패:", error);
    alert("리포트 생성에 실패했습니다.");
  } finally {
    loading.value = false;
  }
};

/**
 * AI 분석 재생성
 */
const regenerateAnalysis = async () => {
  if (!confirm("AI 분석을 다시 생성하시겠습니까?")) return;

  regenerating.value = true;

  try {
    // userId 파라미터 없이 호출 (서버에서 인증 정보로 처리)
    const data = await regenerateAiAnalysis();

    // AI 분석/추천만 업데이트
    aiAnalysis.value = data.aiAnalysis;
    aiRecommendation.value = data.aiRecommendation;

    alert("✅ AI 분석이 새로 생성되었습니다!");
  } catch (error) {
    console.error("AI 분석 재생성 실패:", error);

    // 401 Unauthorized면 로그인 페이지로
    if (error.response?.status === 401) {
      alert("로그인이 필요합니다.");
      router.push("/login");
      return;
    }

    alert("AI 분석 생성에 실패했습니다.");
  } finally {
    regenerating.value = false;
  }
};

/**
 * 콘텐츠 타입 한글 이름 변환
 */
const getContentTypeName = (type) => {
  const nameMap = {
    ATTRACTION: "관광지",
    FESTIVAL: "축제",
    POPUP: "팝업스토어",
  };
  return nameMap[type] || type;
};

// 페이지 로드 시 리포트 조회
onMounted(() => {
  fetchReport();
});
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

.report-page {
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 로딩 */
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 400px;
  gap: 1rem;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f4f6;
  border-top-color: #2563eb;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.loading-text {
  font-size: 16px;
  color: #6b7280;
}

/* 여행 스타일 배너 */
.travel-style-banner {
  padding: 2rem;
  border-radius: 16px;
  display: flex;
  gap: 1.5rem;
  align-items: center;
  margin-bottom: 2rem;
  color: white;
}

.style-cultural_explorer {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.style-festival_hunter {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}
.style-trend_setter {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}
.style-all_rounder {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}
.style-local_enthusiast {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
}
.style-explorer {
  background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
}
.style-beginner {
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
}

.style-icon {
  font-size: 3rem;
}

.style-content {
  flex: 1;
}

.style-title {
  font-size: 24px;
  font-weight: 600;
  margin: 0 0 0.5rem 0;
}

.style-description {
  font-size: 16px;
  opacity: 0.9;
  margin: 0;
}

/* AI 분석 섹션 */
.ai-analysis-section {
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 16px;
  padding: 1.5rem;
  margin-bottom: 2rem;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.section-title {
  font-size: 18px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
}

.ai-regenerate-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  background: linear-gradient(90deg, #2563eb 0%, #7c3aed 100%);
  color: #ffffff;
  border: none;
  border-radius: 9999px;
  font-size: 14px;
  font-weight: 400;
  cursor: pointer;
  transition: all 0.2s ease;
}

.ai-regenerate-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 16px rgba(37, 99, 235, 0.3);
}

.ai-regenerate-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.ai-regenerate-btn svg {
  flex-shrink: 0;
}

.ai-analysis-text {
  font-size: 16px;
  line-height: 1.6;
  color: #374151;
  white-space: pre-wrap;
  margin: 0;
}

/* 통계 카드 그리드 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.stat-card {
  padding: 1.5rem;
  background: #ffffff;
  border: 0.83px solid #e5e7eb;
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.stat-header {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.stat-icon {
  width: 20px;
  height: 20px;
  flex-shrink: 0;
}

.stat-card-blue .stat-icon {
  color: #155dfc;
}
.stat-card-red .stat-icon {
  color: #fb2c36;
}
.stat-card-purple .stat-icon {
  color: #9810fa;
}
.stat-card-yellow .stat-icon {
  color: #fbbf24;
}

.stat-label {
  font-size: 16px;
  font-weight: 400;
  color: #4a5568;
  margin: 0;
}

.stat-value {
  font-size: 24px;
  font-weight: 600;
  color: #1e1e1e;
  margin: 0;
}

/* 하단 섹션 */
.bottom-section {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.preference-card {
  padding: 1.5rem;
  background: #ffffff;
  border: 0.83px solid #e5e7eb;
  border-radius: 16px;
}

.category-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-top: 1rem;
}

.category-item {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.category-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.category-name {
  font-size: 16px;
  font-weight: 400;
  color: #374151;
}

.category-stats {
  font-size: 14px;
  color: #6b7280;
}

.progress-bar {
  width: 100%;
  height: 8px;
  background: #e5e7eb;
  border-radius: 9999px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: #7c3aed;
  border-radius: 9999px;
  transition: width 0.3s ease;
}

.progress-fill.fill-attraction {
  background: #155dfc;
}

.progress-fill.fill-festival {
  background: #fb2c36;
}

.progress-fill.fill-popup {
  background: #9810fa;
}

/* AI 추천 배너 */
.ai-recommendation {
  padding: 1.5rem;
  background: linear-gradient(90deg, #f5f3ff 0%, #eff6ff 100%);
  border: 0.83px solid #ddd6fe;
  border-radius: 16px;
  display: flex;
  gap: 1.5rem;
}

.ai-icon {
  flex-shrink: 0;
  margin-top: 4px;
}

.ai-content {
  flex: 1;
}

.ai-title {
  font-size: 18px;
  font-weight: 600;
  color: #1e1e1e;
  margin: 0 0 0.5rem 0;
}

.ai-text {
  font-size: 16px;
  color: #374151;
  line-height: 1.6;
  margin: 0;
}

/* 반응형 - 태블릿 */
@media (max-width: 1024px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .bottom-section {
    grid-template-columns: 1fr;
  }
}

/* 반응형 - 모바일 */
@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }

  .travel-style-banner {
    flex-direction: column;
    text-align: center;
  }

  .section-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }

  .ai-regenerate-btn {
    width: 100%;
    justify-content: center;
  }

  .ai-recommendation {
    flex-direction: column;
    gap: 1rem;
  }

  .ai-icon {
    margin-top: 0;
  }
}

@media (max-width: 480px) {
  .travel-style-banner {
    padding: 1.5rem;
  }

  .style-icon {
    font-size: 2.5rem;
  }

  .style-title {
    font-size: 20px;
  }

  .style-description {
    font-size: 14px;
  }

  .ai-analysis-section {
    padding: 1rem;
  }

  .section-title {
    font-size: 16px;
  }

  .ai-regenerate-btn {
    padding: 0.625rem 1.25rem;
    font-size: 13px;
  }

  .stat-value {
    font-size: 20px;
  }
}
</style>
