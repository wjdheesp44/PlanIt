<template>
  <div class="report-page">
    <!-- 페이지 헤더 -->
    <div class="page-header">
      <h1 class="page-title">분석 리포트</h1>
      <button class="ai-regenerate-btn" @click="regenerateAnalysis">
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
        <span>AI 분석 다시하기</span>
      </button>
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
      <!-- 선호 카테고리 -->
      <div class="preference-card">
        <h2 class="section-title">선호 카테고리</h2>
        <div class="category-list">
          <div v-for="category in categories" :key="category.name" class="category-item">
            <div class="category-header">
              <span class="category-name">{{ category.name }}</span>
              <span class="category-stats"
                >{{ category.count }}개 ({{ category.percentage }}%)</span
              >
            </div>
            <div class="progress-bar">
              <div class="progress-fill" :style="{ width: category.percentage + '%' }"></div>
            </div>
          </div>
        </div>
      </div>

      <!-- 최근 활동 -->
      <div class="activity-card">
        <h2 class="section-title">최근 활동</h2>
        <div class="activity-list">
          <div v-for="(activity, index) in activities" :key="index" class="activity-item">
            <div class="activity-dot"></div>
            <div class="activity-content">
              <p class="activity-text">{{ activity.text }}</p>
              <p class="activity-date">{{ activity.date }}</p>
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
        <p class="ai-text">
          관광지를 가장 많이 좋아하셨네요! 부산 해운대와 경주 불국사를 추천드립니다. 이번 달 활동이
          지난 달보다 25% 증가했습니다. 계속해서 다양한 스팟을 탐험해보세요!
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const statistics = ref([
  {
    id: 1,
    label: "총 방문한 스팟",
    value: "24",
    color: "blue",
    icon: `<svg width="20" height="20" viewBox="0 0 20 20" fill="none">
      <path d="M16.666 8.333c0 4.161-4.616 8.494-6.166 9.833-.144.087-.32.134-.5.134-.181 0-.357-.047-.501-.134-1.55-1.34-6.166-5.672-6.166-9.833 0-1.768.702-3.464 1.952-4.714C6.536 2.369 8.232 1.667 10 1.667c1.768 0 3.464.702 4.714 1.952 1.25 1.25 1.952 2.946 1.952 4.714z" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
      <path d="M10 10.833a2.5 2.5 0 100-5 2.5 2.5 0 000 5z" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>`,
  },
  {
    id: 2,
    label: "좋아요한 스팟",
    value: "18",
    color: "red",
    icon: `<svg width="20" height="20" viewBox="0 0 20 20" fill="none">
      <path d="M1.667 7.917c.002-.928.282-1.834.807-2.598.524-.763 1.24-1.35 2.066-1.69.826-.34 1.733-.41 2.61-.199.878.21 1.682.694 2.315 1.386.044.046.097.083.155.108.058.025.121.038.185.038s.127-.013.185-.038c.058-.025.111-.062.155-.108.618-.706 1.422-1.196 2.301-1.405.879-.21 1.786-.148 2.614.139.828.287 1.545.878 2.068 1.645.523.767.801 1.675.797 2.604 0 1.908-1.25 3.333-2.5 4.583l-4.576 4.428c-.156.185-.346.338-.561.452-.215.115-.448.19-.688.223-.24.032-.484.022-.719-.032-.236-.054-.46-.15-.66-.283l-3.833-3.563c-1.25-1.25-2.5-2.667-2.5-4.583z" fill="currentColor" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>`,
  },
  {
    id: 3,
    label: "작성한 후기",
    value: "12",
    color: "purple",
    icon: `<svg width="20" height="20" viewBox="0 0 20 20" fill="none">
      <path d="M2.5 2.5v13.333c0 .442.176.866.488 1.179.313.312.737.488 1.179.488H17.5" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
      <path d="M15 14.167V7.5M10.833 14.167V4.167M6.667 14.167v-2.5" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>`,
  },
  {
    id: 4,
    label: "이달의 활동",
    value: "+5",
    color: "green",
    icon: `<svg width="20" height="20" viewBox="0 0 20 20" fill="none">
      <path d="M13.333 5.833h5v5" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
      <path d="M18.333 5.833l-7.083 7.084-4.167-4.167L1.667 14.167" stroke="currentColor" stroke-width="1.667" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>`,
  },
]);

const categories = ref([
  { name: "관광지", count: 10, percentage: 55 },
  { name: "팝업스토어", count: 5, percentage: 28 },
  { name: "축제", count: 3, percentage: 17 },
]);

const activities = ref([
  { text: "감천문화마을 방문 후기 작성", date: "2024.12.01" },
  { text: "강남 팝업스토어 좋아요", date: "2024.11.28" },
  { text: "벚꽃축제 플래너에 추가", date: "2024.11.25" },
  { text: "남산타워 방문 후기 작성", date: "2024.11.20" },
]);

const regenerateAnalysis = () => {
  console.log("AI 분석 재생성");
  // API 호출 또는 분석 재생성 로직
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

.report-page {
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 페이지 헤더 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.page-title {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
}

.ai-regenerate-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 2rem;
  background: linear-gradient(90deg, #2563eb 0%, #7c3aed 100%);
  color: #ffffff;
  border: none;
  border-radius: 9999px;
  font-size: 18px;
  font-weight: 400;
  cursor: pointer;
  transition: all 0.2s ease;
  line-height: 1.56;
}

.ai-regenerate-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 16px rgba(37, 99, 235, 0.3);
}

.ai-regenerate-btn svg {
  flex-shrink: 0;
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

.stat-card-green .stat-icon {
  color: #00a63e;
}

.stat-label {
  font-size: 16px;
  font-weight: 400;
  color: #4a5568;
  margin: 0;
  line-height: 1.5;
}

.stat-value {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
}

/* 하단 섹션 */
.bottom-section {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1.5rem;
  margin-bottom: 2rem;
}

/* 선호 카테고리 */
.preference-card,
.activity-card {
  padding: 1.5rem;
  background: #ffffff;
  border: 0.83px solid #e5e7eb;
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.section-title {
  font-size: 18px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.56;
}

.category-list,
.activity-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
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
  line-height: 1.5;
}

.category-stats {
  font-size: 16px;
  font-weight: 400;
  color: #4a5568;
  line-height: 1.5;
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

/* 최근 활동 */
.activity-item {
  display: flex;
  gap: 1.25rem;
  padding-bottom: 0.75rem;
  border-bottom: 0.83px solid #f3f4f6;
}

.activity-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.activity-dot {
  width: 8px;
  height: 8px;
  background: #9810fa;
  border-radius: 50%;
  flex-shrink: 0;
  margin-top: 6px;
}

.activity-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.activity-text {
  font-size: 14px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.43;
}

.activity-date {
  font-size: 14px;
  font-weight: 400;
  color: #6b7280;
  margin: 0;
  line-height: 1.43;
}

/* AI 추천 배너 */
.ai-recommendation {
  padding: 1.5rem;
  background: linear-gradient(90deg, #f5f3ff 0%, #eff6ff 100%);
  border: 0.83px solid #ddd6fe;
  border-radius: 16px;
  display: flex;
  gap: 1.75rem;
}

.ai-icon {
  width: 17px;
  height: 17px;
  flex-shrink: 0;
  margin-top: 4px;
}

.ai-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.ai-title {
  font-size: 18px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.56;
}

.ai-text {
  font-size: 16px;
  font-weight: 400;
  color: #374151;
  margin: 0;
  line-height: 1.5;
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
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }

  .ai-regenerate-btn {
    width: 100%;
    justify-content: center;
  }

  .stats-grid {
    grid-template-columns: 1fr;
    gap: 1rem;
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
  .page-title {
    font-size: 14px;
  }

  .ai-regenerate-btn {
    padding: 0.625rem 1.5rem;
    font-size: 16px;
  }

  .section-title {
    font-size: 16px;
  }
}
</style>
