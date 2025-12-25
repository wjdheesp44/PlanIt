<template>
  <div class="weather-calendar">
    <h3 class="calendar-title">여행 날씨</h3>

    <div v-if="isLoading" class="calendar-loading">
      <div class="loading-spinner"></div>
    </div>

    <div v-else-if="weatherData.length > 0" class="calendar-carousel">
      <button
        class="carousel-btn prev"
        @click="scrollCarousel('prev')"
        v-if="weatherData.length > 5"
      >
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
          <path
            d="M15 18L9 12L15 6"
            stroke="#4A5565"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </button>

      <div class="calendar-grid" ref="carouselRef">
        <div
          v-for="day in weatherData"
          :key="day.date"
          class="calendar-day"
          :class="getRecommendationClass(day.travelRecommendation)"
          @click="selectDate(day)"
        >
          <div class="day-header">
            <div class="day-info">
              <span class="day-date">{{ formatDate(day.date) }}</span>
              <span class="day-offset">{{ getDaysOffset(day.date) }}</span>
            </div>
            <span class="day-weekday">{{ getWeekday(day.date) }}</span>
          </div>

          <div class="day-weather">
            <span class="weather-emoji">{{ day.weatherEmoji }}</span>
            <span class="weather-condition">{{ day.weatherCondition }}</span>
          </div>

          <div class="day-temp">
            <span class="temp-min">{{ day.minTemperature }}</span>
            <span class="temp-separator">/</span>
            <span class="temp-max">{{ day.maxTemperature }}</span>
          </div>

          <div class="day-recommendation">
            {{ getRecommendationText(day.travelRecommendation) }}
          </div>
        </div>
      </div>

      <button
        class="carousel-btn next"
        @click="scrollCarousel('next')"
        v-if="weatherData.length > 5"
      >
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
          <path
            d="M9 18L15 12L9 6"
            stroke="#4A5565"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </button>
    </div>

    <div v-else class="calendar-empty">날씨 정보를 불러올 수 없습니다.</div>

    <!-- 상세 날씨 모달 (동일) -->
    <div v-if="selectedDay" class="modal-overlay" @click.self="closeModal">
      <div class="modal">
        <div class="modal-header">
          <h4>{{ formatDateFull(selectedDay.date) }} 날씨</h4>
          <button class="close-btn" @click="closeModal">✕</button>
        </div>

        <div class="modal-content">
          <div class="modal-summary">
            <span class="modal-emoji">{{ selectedDay.weatherEmoji }}</span>
            <div class="modal-info">
              <p class="modal-condition">{{ selectedDay.weatherCondition }}</p>
              <p v-if="selectedDay.minTemperature && selectedDay.maxTemperature" class="modal-temp">
                {{ selectedDay.minTemperature }} ~ {{ selectedDay.maxTemperature }}
              </p>
            </div>
          </div>

          <div
            v-if="selectedDay.hourlyForecasts && selectedDay.hourlyForecasts.length > 0"
            class="hourly-forecasts"
          >
            <h5>시간대별 날씨</h5>
            <div class="hourly-list">
              <div
                v-for="hour in selectedDay.hourlyForecasts"
                :key="hour.forecastTime"
                class="hourly-item"
              >
                <span class="hourly-time">{{ formatTime(hour.forecastTime) }}</span>
                <span class="hourly-emoji">{{ hour.weatherEmoji }}</span>
                <span class="hourly-temp">{{ hour.temperature ?? "- " }}°C</span>
                <span class="hourly-precip">{{ hour.precipitationProbability }}%</span>
              </div>
            </div>
          </div>

          <div
            class="modal-recommendation"
            :style="{
              background: selectedDay.recommendationColor + '20',
              borderLeft: '4px solid ' + selectedDay.recommendationColor,
            }"
          >
            <strong>여행 추천도:</strong> {{ selectedDay.travelRecommendation }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import weatherApi from "@/api/spot/weatherApi";

const props = defineProps({
  gugunId: {
    type: Number,
    required: true,
  },
});

const isLoading = ref(false);
const weatherData = ref([]);
const selectedDay = ref(null);
const carouselRef = ref(null);

// 캐러셀 스크롤
const scrollCarousel = (direction) => {
  if (!carouselRef.value) return;

  const scrollAmount = 300;
  if (direction === "next") {
    carouselRef.value.scrollBy({ left: scrollAmount, behavior: "smooth" });
  } else {
    carouselRef.value.scrollBy({ left: -scrollAmount, behavior: "smooth" });
  }
};

// 날씨 데이터 로드
const loadWeatherData = async () => {
  if (!props.gugunId) return;

  try {
    isLoading.value = true;
    const today = new Date();
    const endDate = new Date();
    endDate.setDate(today.getDate() + 9); // 10일치

    const response = await weatherApi.getWeatherCalendar(
      props.gugunId,
      formatDateParam(today),
      formatDateParam(endDate)
    );

    weatherData.value = response.data;
  } catch (error) {
    console.error("Failed to load weather data:", error);
    weatherData.value = [];
  } finally {
    isLoading.value = false;
  }
};

// 날짜 포맷 (API 파라미터용)
const formatDateParam = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

// 날짜 포맷 (표시용 - MM/DD)
const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${month}/${day}`;
};

// 오늘 기준 며칠 후인지 계산
const getDaysOffset = (dateStr) => {
  const today = new Date();
  today.setHours(0, 0, 0, 0); // 시간 제거

  const targetDate = new Date(dateStr);
  targetDate.setHours(0, 0, 0, 0); // 시간 제거

  const diffTime = targetDate - today;
  const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

  if (diffDays === 0) return "오늘";
  if (diffDays === 1) return "내일";
  if (diffDays === 2) return "모레";
  return `${diffDays}일 후`;
};

// 날짜 포맷 (상세 - YYYY년 MM월 DD일)
const formatDateFull = (dateStr) => {
  const date = new Date(dateStr);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const weekday = getWeekday(dateStr);
  return `${year}년 ${month}월 ${day}일 (${weekday})`;
};

// 요일 구하기
const getWeekday = (dateStr) => {
  const weekdays = ["일", "월", "화", "수", "목", "금", "토"];
  const date = new Date(dateStr);
  return weekdays[date.getDay()];
};

// 시간 포맷 (0600 → 06:00)
const formatTime = (timeStr) => {
  if (!timeStr) return "";
  const hour = timeStr.substring(0, 2);
  const minute = timeStr.substring(2, 4);
  return `${hour}:${minute}`;
};

// 추천도 클래스
const getRecommendationClass = (recommendation) => {
  const classMap = {
    "여행하기 좋음": "recommendation-good",
    보통: "recommendation-moderate",
    주의: "recommendation-caution",
    비추천: "recommendation-bad",
  };
  return classMap[recommendation] || "";
};

// 추천도 텍스트 (짧게)
const getRecommendationText = (recommendation) => {
  const textMap = {
    "여행하기 좋음": "좋음",
    보통: "보통",
    주의: "주의",
    비추천: "비추천",
  };
  return textMap[recommendation] || "";
};

// 날짜 선택
const selectDate = (day) => {
  selectedDay.value = day;
};

// 모달 닫기
const closeModal = () => {
  selectedDay.value = null;
};

// gugunId 변경 감지
watch(
  () => props.gugunId,
  () => {
    loadWeatherData();
  },
  { immediate: true }
);
</script>

<style scoped>
.weather-calendar {
  margin-bottom: 3rem;
}

.calendar-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: black;
  margin-bottom: 1.5rem;
}

.calendar-loading {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f4f6;
  border-top: 4px solid #2563eb;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

/* 캐러셀 컨테이너 */
.calendar-carousel {
  position: relative;
}

/* 캐러셀 버튼 */
.carousel-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  border: none;
  background: white;
  box-shadow: 0 4px 6px -4px rgba(0, 0, 0, 0.1), 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
  transition: all 0.2s;
}

.carousel-btn:hover {
  transform: translateY(-50%) scale(1.05);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
}

.carousel-btn.prev {
  left: -1.25rem;
}

.carousel-btn.next {
  right: -1.25rem;
}

/* 슬라이드 그리드 */
.calendar-grid {
  display: flex;
  gap: 1rem;
  overflow-x: auto;
  scroll-behavior: smooth;
  scrollbar-width: none;
  -ms-overflow-style: none;
  padding: 0.5rem 0;
}

.calendar-grid::-webkit-scrollbar {
  display: none;
}

.calendar-day {
  flex: 0 0 160px;
  background: white;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  padding: 1rem;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.calendar-day:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 추천도별 색상 */
.recommendation-good {
  border-color: #4caf50;
  background: linear-gradient(135deg, #ffffff 0%, #f1f8f4 100%);
}

.recommendation-moderate {
  border-color: #ffc107;
  background: linear-gradient(135deg, #ffffff 0%, #fff9e6 100%);
}

.recommendation-caution {
  border-color: #ff9800;
  background: linear-gradient(135deg, #ffffff 0%, #fff3e6 100%);
}

.recommendation-bad {
  border-color: #f44336;
  background: linear-gradient(135deg, #ffffff 0%, #ffebee 100%);
}

.day-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #e5e7eb;
}

.day-info {
  display: flex;
  flex-direction: column;
  gap: 0.125rem;
}

.day-date {
  font-size: 0.875rem;
  font-weight: 600;
  color: #374151;
}

.day-offset {
  font-size: 0.625rem;
  color: #7c3aed;
  font-weight: 500;
}

.day-weekday {
  font-size: 0.75rem;
  color: #9ca3af;
}

.day-weather {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.25rem;
  padding: 0.5rem 0;
}

.weather-emoji {
  font-size: 2rem;
}

.weather-condition {
  font-size: 0.875rem;
  color: #4b5563;
}

.day-temp {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.25rem;
  font-size: 0.875rem;
  color: #374151;
}

.temp-min {
  color: #3b82f6;
}

.temp-separator {
  color: #9ca3af;
}

.temp-max {
  color: #ef4444;
}

.day-recommendation {
  text-align: center;
  font-size: 0.75rem;
  font-weight: 600;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  background: rgba(0, 0, 0, 0.05);
}

.calendar-empty {
  text-align: center;
  padding: 3rem;
  color: #9ca3af;
}

/* 모달 (기존과 동일) */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 3000;
}

.modal {
  background: white;
  width: 90%;
  max-width: 500px;
  border-radius: 16px;
  overflow: hidden;
  max-height: 80vh;
  display: flex;
  flex-direction: column;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem;
  border-bottom: 1px solid #e5e7eb;
}

.modal-header h4 {
  font-size: 1.125rem;
  font-weight: 600;
  color: #1f2937;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #9ca3af;
  cursor: pointer;
  padding: 0;
  width: 2rem;
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
  transition: background 0.2s;
}

.close-btn:hover {
  background: #f3f4f6;
}

.modal-content {
  padding: 1.5rem;
  overflow-y: auto;
  flex: 1;
}

.modal-summary {
  display: flex;
  gap: 1rem;
  align-items: center;
  padding: 1rem;
  background: #f9fafb;
  border-radius: 12px;
  margin-bottom: 1.5rem;
}

.modal-emoji {
  font-size: 3rem;
}

.modal-info {
  flex: 1;
}

.modal-condition {
  font-size: 1.125rem;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 0.25rem;
}

.modal-temp {
  font-size: 1rem;
  color: #6b7280;
}

.hourly-forecasts {
  margin-bottom: 1.5rem;
}

.hourly-forecasts h5 {
  font-size: 0.875rem;
  font-weight: 600;
  color: #6b7280;
  margin-bottom: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.hourly-list {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.hourly-item {
  display: grid;
  grid-template-columns: 60px 40px 60px 1fr;
  align-items: center;
  padding: 0.75rem;
  background: white;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 0.875rem;
}

.hourly-time {
  color: #374151;
  font-weight: 500;
}

.hourly-emoji {
  font-size: 1.25rem;
}

.hourly-temp {
  color: #1f2937;
  font-weight: 600;
}

.hourly-precip {
  color: #3b82f6;
  text-align: right;
}

.modal-recommendation {
  padding: 1rem;
  border-radius: 8px;
  font-size: 0.875rem;
  color: #1f2937;
}

.modal-recommendation strong {
  font-weight: 600;
}

/* 반응형 */
@media (max-width: 1024px) {
  .calendar-day {
    flex: 0 0 140px;
  }

  .carousel-btn.prev {
    left: 0;
  }

  .carousel-btn.next {
    right: 0;
  }
}

@media (max-width: 768px) {
  .calendar-day {
    flex: 0 0 130px;
    padding: 0.75rem;
  }

  .weather-emoji {
    font-size: 1.5rem;
  }
}

@media (max-width: 480px) {
  .calendar-day {
    flex: 0 0 120px;
    padding: 0.5rem;
  }

  .day-date {
    font-size: 0.75rem;
  }

  .day-offset {
    font-size: 0.5625rem;
  }

  .weather-emoji {
    font-size: 1.25rem;
  }

  .weather-condition {
    font-size: 0.75rem;
  }

  .day-temp {
    font-size: 0.75rem;
  }

  .carousel-btn {
    width: 2rem;
    height: 2rem;
  }

  .carousel-btn.prev {
    left: -0.5rem;
  }

  .carousel-btn.next {
    right: -0.5rem;
  }
}
</style>
